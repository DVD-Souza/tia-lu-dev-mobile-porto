enum class OrderStatus {
    ACCEPTED, INPROGRESS, DONE, WAITINGCOURIER, OUTFORDELIVERY, DELIVERED
}

data class Item(
    val code: Int,
    val name: String,
    val description: String,
    val price: Double,
    val amount: Int
)

data class Order(
    val code: Int,
    var status: OrderStatus,
    val value: Double,
    val items: ArrayList<Item>,
    val discount: Boolean
)

fun main() {
    var option = -1
    val items = arrayListOf<Item>()
    val orders = arrayListOf<Order>()
    var itemCodeGenerator = 0
    var orderCodeGenerator = 0

    do {
        println("=== MENU ===")
        println("1: CADASTRAR ITEM")
        println("2: ATUALIZAR ITEM")
        println("3: CRIAR PEDIDO")
        println("4: ATUALIZAR PEDIDO")
        println("5: CONSULTAR PEDIDO(S)")
        println("0: ENCERRAR O SISTEMA")
        print("Qual opção deseja selecionar: ")

        val optionInput = readln().trim()
        option = optionInput.toIntOrNull() ?: run {
            println("Entrada inválida, digite um número.")
            continue
        }

        when (option) {
            1 -> {
                do {
                    println("=== Cadastro de item ===")
                    print("Nome: ")
                    val name = readln()

                    print("Descrição: ")
                    val description = readln()

                    var price: Double
                    while (true) {
                        print("Preço: ")
                        val p = readln().toDoubleOrNull()
                        if (p != null && p >= 0.0) {
                            price = p
                            break
                        }
                        println("Entrada inválida. Digite um número decimal não negativo.")
                    }

                    var amount: Int
                    while (true) {
                        print("Quantidade em estoque: ")
                        val a = readln().toIntOrNull()
                        if (a != null && a >= 0) {
                            amount = a
                            break
                        }
                        println("Entrada inválida. Digite um número inteiro não negativo.")
                    }

                    itemCodeGenerator++
                    items.add(Item(itemCodeGenerator, name, description, price, amount))
                    println("Item cadastrado com sucesso, código: $itemCodeGenerator")

                    print("Deseja cadastrar outro item? (S/N): ")
                    val continuar = readln().trim().uppercase()
                    if (continuar != "S") break
                } while (true)
            }

            2 -> {
                if (items.isEmpty()) {
                    println("Nenhum item cadastrado para atualizar.")
                } else {
                    println("=== Itens cadastrados ===")
                    println("Código".padEnd(8) + "Nome".padEnd(20) + "Descrição".padEnd(30) + "Preço".padStart(10) + "Qtd".padStart(8))
                    println("-".repeat(80))
                    for (it in items) {
                        println(
                            it.code.toString().padEnd(8) +
                                    it.name.padEnd(20) +
                                    it.description.padEnd(30) +
                                    ("R$ " + "%.2f".format(it.price)).padStart(10) +
                                    it.amount.toString().padStart(8)
                        )
                    }

                    print("Qual o código do item que deseja atualizar: ")
                    val chosenItemCode = readln().toIntOrNull()
                    if (chosenItemCode == null) {
                        println("Entrada inválida.")
                    } else {
                        val index = items.indexOfFirst { it.code == chosenItemCode }
                        if (index == -1) {
                            println("Item não encontrado.")
                        } else {
                            print("Novo nome: ")
                            val name = readln()
                            print("Nova descrição: ")
                            val description = readln()

                            var price: Double
                            while (true) {
                                print("Novo preço: ")
                                val p = readln().toDoubleOrNull()
                                if (p != null && p >= 0.0) {
                                    price = p
                                    break
                                }
                                println("Entrada inválida.")
                            }

                            var amount: Int
                            while (true) {
                                print("Nova quantidade: ")
                                val a = readln().toIntOrNull()
                                if (a != null && a >= 0) {
                                    amount = a
                                    break
                                }
                                println("Entrada inválida.")
                            }

                            items[index] = Item(chosenItemCode, name, description, price, amount)
                            println("Item atualizado com sucesso.")
                        }
                    }
                }
            }

            3 -> {
                if (items.isEmpty()) {
                    println("Nenhum item cadastrado. Cadastre itens antes de criar pedidos.")
                } else {
                    val orderItems = arrayListOf<Item>()
                    var orderTotal = 0.0

                    do {
                        println("=== MENU DE PRODUTOS ===")
                        println("Código".padEnd(8) + "Nome".padEnd(20) + "Descrição".padEnd(30) + "Preço".padStart(10) + "Qtd".padStart(8))
                        println("-".repeat(80))
                        for (it in items) {
                            println(
                                it.code.toString().padEnd(8) +
                                        it.name.padEnd(20) +
                                        it.description.padEnd(30) +
                                        ("R$ " + "%.2f".format(it.price)).padStart(10) +
                                        it.amount.toString().padStart(8)
                            )
                        }

                        print("Código do item para adicionar (ou ENTER para finalizar): ")
                        val line = readln()
                        if (line.isBlank()) break

                        val code = line.toIntOrNull()
                        if (code == null) {
                            println("Entrada inválida!")
                            continue
                        }

                        val item = items.find { it.code == code }
                        if (item == null) {
                            println("Código inválido.")
                        } else {
                            orderItems.add(item)
                            orderTotal += item.price
                            println("Item '${item.name}' adicionado. Total parcial: R$ %.2f".format(orderTotal))
                        }
                    } while (true)

                    if (orderItems.isEmpty()) {
                        println("Pedido não criado: nenhum item selecionado.")
                    } else {
                        print("Você deseja usar um cupom de desconto? (S/N): ")
                        val hasDiscount = readln().trim().equals("S", ignoreCase = true)
                        if (hasDiscount) {
                            orderTotal *= 0.90
                            println("Você ganhou 10% de desconto.")
                        }

                        orderCodeGenerator++
                        orders.add(Order(orderCodeGenerator, OrderStatus.ACCEPTED, orderTotal, orderItems, hasDiscount))
                        println("O pedido de código $orderCodeGenerator foi aceito, valor final: R$ %.2f".format(orderTotal))
                    }
                }
            }

            4 -> {
                if (orders.isEmpty()) {
                    println("Nenhum pedido cadastrado para atualizar.")
                } else {
                    println("Código".padEnd(8) + "Status".padEnd(20) + "Valor".padStart(12))
                    println("-".repeat(40))
                    for (order in orders) {
                        println(
                            order.code.toString().padEnd(8) +
                                    order.status.toString().padEnd(20) +
                                    ("R$ " + "%.2f".format(order.value)).padStart(12)
                        )
                    }

                    print("Qual o código do pedido a ser atualizado: ")
                    val code = readln().toIntOrNull()
                    if (code == null) {
                        println("Entrada inválida.")
                    } else {
                        val order = orders.find { it.code == code }
                        if (order == null) {
                            println("Pedido não encontrado.")
                        } else {
                            println("Qual o novo Status do pedido:")
                            println(" 1 - INPROGRESS")
                            println(" 2 - DONE")
                            println(" 3 - WAITINGCOURIER")
                            println(" 4 - OUTFORDELIVERY")
                            println(" 5 - DELIVERED")
                            val optionStatus = readln().toIntOrNull()
                            val newStatus = when (optionStatus) {
                                1 -> OrderStatus.INPROGRESS
                                2 -> OrderStatus.DONE
                                3 -> OrderStatus.WAITINGCOURIER
                                4 -> OrderStatus.OUTFORDELIVERY
                                5 -> OrderStatus.DELIVERED
                                else -> null
                            }
                            if (newStatus == null) {
                                println("Opção inválida.")
                            } else {
                                order.status = newStatus
                                println("Status atualizado para ${order.status}.")
                            }
                        }
                    }
                }
            }

            5 -> {
                if (orders.isEmpty()) {
                    println("Nenhum pedido cadastrado.")
                } else {
                    println("=== Filtro de pedidos ===")
                    println("1 - TODOS")
                    OrderStatus.entries.forEachIndexed { index, status ->
                        println("${index + 2} - $status")
                    }

                    print("Selecione uma opção: ")
                    val statusInput = readln().toIntOrNull()
                    if (statusInput == null) {
                        println("Entrada inválida.")
                    } else {
                        val filter: OrderStatus? = when (statusInput) {
                            1 -> null // TODOS
                            in 2..(OrderStatus.entries.size + 1) -> OrderStatus.entries[statusInput - 2]
                            else -> {
                                println("Opção inválida.")
                                null
                            }
                        }

                        val filteredOrders = if (filter == null) orders else orders.filter { it.status == filter }

                        if (filteredOrders.isEmpty()) {
                            println("Nenhum pedido encontrado com este STATUS.")
                        } else {
                            for (order in filteredOrders) {
                                println("Código: ${order.code}")
                                println("Status: ${order.status}")
                                println("Valor: R$ %.2f".format(order.value))
                                println("Itens [")
                                for (item in order.items) {
                                    println(
                                        "  Código: ${item.code}, " +
                                                "Nome: ${item.name}, " +
                                                "Preço: R$ %.2f".format(item.price)
                                    )
                                }
                                println("]")
                                println("Desconto: ${if (order.discount) "10%" else "0%"}")
                                println("-".repeat(40))
                            }
                        }
                    }
                }
            }

            0 -> println("O Sistema será encerrado.")
            else -> println("Opção inválida.")
        }
    } while (option != 0)
}