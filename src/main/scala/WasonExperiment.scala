// src/main/scala/WasonExperiment.scala

import com.raquo.laminar.api.L._
import org.scalajs.dom

object WasonExperiment {

  // ---- Modelo de datos ----
  // Representa un ítem seleccionable en cualquiera de las dos pruebas
  case class SelectableItem(id: String, display: String, isEmoji: Boolean = false)

  // ---- Estado de la Aplicación ----
  // Usamos Var de Laminar para mantener un estado reactivo
  val currentTask = Var("cards") // 'cards' o 'social'

  // Estado para la tarea de las cartas
  val selectedCards = Var(Set.empty[String])
  val cardFeedback = Var("")
  val cardFeedbackClass = Var("")

  // Estado para la tarea social (bebidas)
  val selectedSocial = Var(Set.empty[String])
  val socialFeedback = Var("")
  val socialFeedbackClass = Var("")

  // ---- Lógica de las Tareas ----
  // La regla es: "SI (impar) ENTONCES (vocal)"
  // Para falsearla, debemos buscar un caso de "impar Y NO vocal".
  // Por lo tanto, hay que dar la vuelta a:
  // 1. El número impar (3) para ver si detrás hay una consonante.
  // 2. La consonante (H) para ver si detrás hay un número impar.
  val correctCards = Set("3", "H")

  // La regla es: "SI (consume alcohol) ENTONCES (es mayor de 18)"
  // Para falsearla, debemos buscar un caso de "consume alcohol Y NO es mayor de 18".
  // Por lo tanto, hay que verificar a:
  // 1. La persona que consume alcohol (cerveza) para ver si es menor.
  // 2. La persona que es menor (adolescente) para ver qué consume.
  val correctSocial = Set("Adolescente", "Cerveza")

  // ---- Componentes de la Interfaz (Vistas) ----

  // Función genérica para renderizar un ítem (carta o emoji)
  def renderItem(item: SelectableItem, selectionVar: Var[Set[String]]): Element =
    div(
      cls := "item",
      cls.toggle("emoji") := item.isEmoji,
      // La clase 'selected' se activa/desactiva reactivamente
      cls.toggle("selected") <-- selectionVar.signal.map(_.contains(item.id)),
      onClick --> { _ =>
        // Al hacer clic, actualizamos el conjunto de ítems seleccionados
        selectionVar.update(current =>
          if (current.contains(item.id)) current - item.id
          else current + item.id
        )
      },
      item.display
    )

  // Vista para la Tarea de Wason clásica (cartas)
  def wasonCardTaskView: Element = {
    val items = List(
      SelectableItem("A", "A"),
      SelectableItem("H", "H"),
      SelectableItem("3", "3"),
      SelectableItem("8", "8")
    )

    div(
      cls := "task-container",
      div(
        cls := "instructions",
        p("Te muestran un grupo de cuatro cartas en una mesa. Cada una de ellas tiene un número de un lado y una letra del otro. Las caras visibles de las cartas muestran A, H, 3, y 8."),
        p(strong("¿A qué cartas debería dar vuelta para comprobar la veracidad de la proposición que SI una carta muestra un número impar por un lado, ENTONCES la cara opuesta debe mostrar una vocal?"))
      ),
      div(
        cls := "items-grid",
        items.map(renderItem(_, selectedCards)) // Renderiza cada carta
      ),
      button(
        cls := "verify-button",
        "Verificar Respuesta",
        onClick --> { _ =>
          if (selectedCards.now() == correctCards) {
            cardFeedback.set("¡Correcto! Para falsear la regla, debes buscar un número impar con una consonante detrás.")
            cardFeedbackClass.set("feedback correct")
          } else {
            cardFeedback.set("Respuesta incorrecta. Inténtalo de nuevo.")
            cardFeedbackClass.set("feedback incorrect")
          }
        }
      ),
      div(
        cls <-- cardFeedbackClass.signal,
        child.text <-- cardFeedback.signal
      )
    )
  }

  // Vista para la Tarea de Wason social (bebidas)
  def socialContextTaskView: Element = {
    val items = List(
      SelectableItem("Adulto", "👨", isEmoji = true),
      SelectableItem("Adolescente", "👦", isEmoji = true),
      SelectableItem("Cerveza", "🍺", isEmoji = true),
      SelectableItem("Leche", "🥛", isEmoji = true)
    )

    div(
      cls := "task-container",
      div(
        cls := "instructions",
        p("Trabajas como camarero en una fuente de soda y debes asegurarte que se cumpla la regla: ", strong("sólo los mayores de 18 años pueden consumir alcohol"), "."),
        p("Hay cuatro situaciones representadas: un adulto, un adolescente, una persona bebiendo cerveza y otra bebiendo leche."),
        p(strong("¿A quién o qué bebida debes investigar para asegurar que se cumpla la regla?"))
      ),
      div(
        cls := "items-grid",
        items.map(renderItem(_, selectedSocial)) // Renderiza cada emoji
      ),
      button(
        cls := "verify-button",
        "Verificar Respuesta",
        onClick --> { _ =>
          if (selectedSocial.now() == correctSocial) {
            socialFeedback.set("¡Correcto! Debes revisar qué bebe el menor y la edad de quien bebe alcohol.")
            socialFeedbackClass.set("feedback correct")
          } else {
            socialFeedback.set("Respuesta incorrecta. Inténtalo de nuevo.")
            socialFeedbackClass.set("feedback incorrect")
          }
        }
      ),
      div(
        cls <-- socialFeedbackClass.signal,
        child.text <-- socialFeedback.signal
      )
    )
  }

  // ---- Aplicación Principal ----
  def appElement: Element =
    div(
      h1("Tarea de Selección de Wason"),
      div(
        cls := "button-group",
        // Botones para cambiar entre las dos vistas
        button(
          "Problema Lógico (Cartas)",
          cls := "switch-button",
          cls.toggle("active") <-- currentTask.signal.map(_ == "cards"),
          onClick --> { _ => currentTask.set("cards") }
        ),
        button(
          "Problema Social (Bebidas)",
          cls := "switch-button",
          cls.toggle("active") <-- currentTask.signal.map(_ == "social"),
          onClick --> { _ => currentTask.set("social") }
        )
      ),
      // El contenido principal cambia dinámicamente según el estado de 'currentTask'
      child <-- currentTask.signal.map {
        case "cards"  => wasonCardTaskView
        case "social" => socialContextTaskView
      }
    )

  // ---- Punto de Entrada ----
  def main(args: Array[String]): Unit = {
    // Renderiza el elemento principal de la app en el contenedor del HTML
    renderOnDomContentLoaded(
      dom.document.getElementById("app-container"),
      appElement
    )
  }
}