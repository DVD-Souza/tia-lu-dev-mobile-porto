enum class Status{
    ACEITO,
    FAZENDO,
    FEITO,
    ESPERANDO_ENTREGADOR,
    SAIU_PARA_ENTREGA,
    ENTREGUE
}

data class Item(
    val code: Int,
    val name: String,
    val description: String,
    val price: Float,
    val amount: Int
)

data class Order(
    val code: Int,
    var status: Status,
    val value: Double,
    val itens: ArrayList<Item>,
    val discount: Boolean
)

fun main() {
    var option: Int = -1
    val menu: ArrayList<Item> = ArrayList()
    var itemCode: Int = 0
    val orders: ArrayList<Order> = ArrayList()
    var orderCode: Int = 0

    do {
        println("==================================================");
        println("                    MENU                          ");
        println("Opção 1: CADASTRAR ITEM");
        println("Opção 2: ATUALIZAR ITEM")
        println("OPÇÂO 3: CRIAR PEDIDO")
        println("Opção 4: ATUALIZAR PEDIDO")
        println("Opção 5: CONSULTAR PEDIDO(S)")
        println("Opção 0: ENCERRAR O SISTEMA")
        println("==================================================");
        print("Qual Opção deseja selecionar: ")

        try {
            option = readln().toInt()
        } catch (e: NumberFormatException) {
            println("Entrada inválida, digite um número.")
            continue
        }

        when (option) {
            1 -> {
                print("Quantos itens deseja cadastrar no sistema: ")
                val qtd = readln().toInt()
                for (i in 1..qtd) {
                    println("Sobre o produto $i.")
                    itemCode++
                    print("Qual o nome do produto: ")
                    val name = readln()
                    print("Qual a descricao do produto: ")
                    val description = readln()
                    print("Qual o preco do produto: ")
                    val price = readln().toFloat()
                    print("Qual a quantidade em estoque: ")
                    val amount = readln().toInt()
                    menu.add(Item(itemCode, name, description, price, amount))
                    println("Item cadastrado com sucesso, codigo: $itemCode")
                }
            }

            2 -> {
                if (menu.isEmpty()) {
                    println("Nenhum item cadastrado para atualizar.")
                    continue
                }

                for (item in menu) {
                    println(
                        "Codigo: ${item.code}, " +
                                "Nome: ${item.name}, " +
                                "Descrição: ${item.description}, " +
                                "Preço: ${item.price}, " +
                                "Quantidade em estoque: ${item.amount}"
                    )
                }

                print("Qual o codigo do item que deseja atualizar: ")
                val thisItem = readln().toInt()
                var ishere = false

                for (i in 0 until menu.size) {
                    if (menu[i].code == thisItem) {
                        print("Qual o nome do novo produto: ")
                        val name = readln()
                        print("Qual a descricao do novo produto: ")
                        val description = readln()
                        print("Qual o preco do novo produto: ")
                        val price = readln().toFloat()
                        print("Qual a quantidade em estoque do novo produto: ")
                        val amount = readln().toInt()
                        val newItem = Item(thisItem, name, description, price, amount)
                        menu[i] = newItem
                        ishere = true
                        println("Item atualizado com sucesso, codigo: $thisItem")
                        break
                    }
                }

                if (!ishere) {
                    println("O código informado é inválido.")
                }
            }

            3 -> {
                if (menu.isEmpty()) {
                    println("Nenhum item cadastrado, cadastre antes de fazer pedidos.")
                    continue
                }

                print("Quantos itens deseja adicionar ao pedido: ")
                val qtd = readln().toInt()
                var value = 0.0
                var hasDiscount = false
                val itensOrder: ArrayList<Item> = ArrayList()
                orderCode++

                for (i in 1..qtd) {
                    for (item in menu) {
                        println(
                            "Codigo: ${item.code}, " +
                                    "Nome: ${item.name}, " +
                                    "Descrição: ${item.description}, " +
                                    "Preço: ${item.price}, " +
                                    "Quantidade em estoque: ${item.amount}"
                        )
                    }

                    print("Qual codigo do $i° item que você quer adicionar: ")
                    val code = readln().toInt()

                    val item = menu.find { it.code == code }
                    if (item != null) {
                        itensOrder.add(item)
                        value += item.price
                    } else {
                        println("O código do item inserido é inválido")
                    }
                }

                print("Você deseja usar um cupom de desconto? (S/N)")
                when (readln().uppercase()) {
                    "S" -> {
                        hasDiscount = true
                        println("Você ganhou 10% de desconto.")
                        value *= 0.90
                    }

                    "N" -> {
                        hasDiscount = false
                        println("Você escolheu não usar o cupom.")
                    }

                    else -> println("Opção inválida")
                }

                value = Math.round(value * 100) / 100.0

                if (itensOrder.isEmpty()) {
                    println("Pedido não criado, nenhum item válido selecionado.")
                    continue
                }

                orders.add(Order(orderCode, Status.ACEITO, value, itensOrder, hasDiscount))
                println("O pedido de código $orderCode foi aceito, valor final: R$ $value")
            }

            4 -> {
                if (orders.isEmpty()) {
                    println("Nenhum pedido cadastrado para atualizar.")
                    continue
                }

                for (order in orders) {
                    println(
                        "Codigo: ${order.code} " +
                                "Status: ${order.status} " +
                                "Valor: ${order.value}"
                    )
                }

                println("Qual o codigo do pedido a ser atualizado: ")
                val code = readln().toInt()
                var ishere = false

                for (i in 0 until orders.size) {
                    if (orders[i].code == code) {
                        println("Qual o novo Status do pedido: ")
                        println(" 1 - FAZENDO.")
                        println(" 2 - FEITO.")
                        println(" 3 - ESPERANDO ENTREGADOR.")
                        println(" 4 - SAIU PARA ENTREGA.")
                        println(" 5 - ENTREGUE.")
                        print("Digite a opção: ")
                        when (readln().toInt()) {
                            1 -> orders[i].status = Status.FAZENDO
                            2 -> orders[i].status = Status.FEITO
                            3 -> orders[i].status = Status.ESPERANDO_ENTREGADOR
                            4 -> orders[i].status = Status.SAIU_PARA_ENTREGA
                            5 -> orders[i].status = Status.ENTREGUE
                        }
                        ishere = true
                        println("O pedido de código $code teve seu status atualizado para ${orders[i].status}")
                        break
                    }
                }

                if (!ishere) {
                    println("O código informado é inválido.")
                }
            }

            5 -> {
                if (orders.isEmpty()) {
                    println("Nenhum pedido cadastrado.")
                    continue
                }

                println("Filtro de pedidos:")
                println(" 1 - TODOS")
                println(" 2 - ACEITO")
                println(" 3 - FAZENDO.")
                println(" 4 - FEITO.")
                println(" 5 - ESPERANDO ENTREGADOR.")
                println(" 6 - SAIU PARA ENTREGA.")
                println(" 7 - ENTREGUE.")

                val status = readln().toInt()
                var filter: Status? = null
                var ishere = false


                when (status) {
                    2 -> filter = Status.ACEITO
                    3 -> filter = Status.FAZENDO
                    4 -> filter = Status.FEITO
                    5 -> filter = Status.ESPERANDO_ENTREGADOR
                    6 -> filter = Status.SAIU_PARA_ENTREGA
                    7 -> filter = Status.ENTREGUE
                }

                for (order in orders) {
                    if (filter == null || order.status == filter) {
                        println("Codigo: ${order.code}")
                        println("Status: ${order.status}")
                        println("Valor: ${order.value}")
                        println("Itens[ ")
                        for (item in order.itens) {
                            print(
                                "          Codigo: ${item.code}, " +
                                          "Nome: ${item.name}, " +
                                          "Descrição: ${item.description}, " +
                                          "Preço: ${item.price} ]"
                            )
                        }
                        println("Desconto: ${if (order.discount) "10%" else "0%"}")
                        ishere = true
                    }
                }
                if (!ishere){
                    println("Nenhum pedido encontrado com este STATUS.")
                }
            }

            0 -> {
                println("O Sistema será encerrado")
            }
        }
    } while (option != 0)
}