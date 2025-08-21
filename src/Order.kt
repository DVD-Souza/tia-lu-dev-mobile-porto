data class Order(
    val code: Int,
    val status: String,
    val value: Float,
    val itens: List<Item>,
    val discount: Float
)