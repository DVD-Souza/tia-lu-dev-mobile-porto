// gostei muito de ver esse enum do Status, eu acho que aqui o nome poderia ser OrderStatus
enum class Status{
    ACEITO,
    FAZENDO,
    FEITO,
    ESPERANDO_ENTREGADOR,
    SAIU_PARA_ENTREGA,
    ENTREGUE
} // estamos com uma mistura de português e inglês

// gostei do uso de data classes, pois melhora a legibilidade do código
data class Item(
    val code: Int,
    val name: String,
    val description: String,
    val price: Float,
    val amount: Int
)

// gostei de terem visto que precisamos de um código para o pedido
data class Order(
    val code: Int,
    var status: Status,
    val value: Double,
    val itens: ArrayList<Item>, // interessante que seja um ArrayList<> porque é um tipo de mutableList implementado
    // com arrays
    val discount: Boolean
)

fun main() {
    var option: Int = -1
    val menu: ArrayList<Item> = ArrayList() // por que o nome dessa variável é menu? não deveria ser items?
    val orders: ArrayList<Order> = ArrayList() // era legal as listas terem sido declaradas uma ao lado da outra

    var itemCode: Int = 0 // gostei, mas poderia ter colocado o nome itemCodeGenerator
    var orderCode: Int = 0 // gostei, mas poderia ter colocado o nome orderCodeGenerator

    do {

        // eu tiraria esses Opção 1: e deixaria apenas 1. melhora a legibilidade
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
            // é interessante essa dinâmica do erro com um continue para voltar o menu, mas vocês tem
            // que tomar cuidado com a forma de implementar isso
            println("Entrada inválida, digite um número.")
            continue
        }

        // quando a opção
        when (option) {
            1 -> {

                // aqui, por exemplo, é interessante ter uma validação também, pois a quantidade de itens para ser lido
                // poderia também dar um erro de conversão
                print("Quantos itens deseja cadastrar no sistema: ")
                val qtd = readln().toInt()

                // achei a estratégia interessante, mas fico me perguntando se não seria cadastrar um produto por vez
                // e voltar perguntar se não queria cadastrar mais um produto.
                // por que eu digo isso? porque pensando na experiência do usuário, eu não sei se eles sabem logo de
                // cara a quantidade exata de produtos que querem cadastrar. Seria melhor repetir o processo
                // enquanto o usuário diz que quer registrar um novo pedido.
                for (i in 1..qtd) {
                    println("Sobre o produto $i.")
                    itemCode++ // eu acho melhor incrementar o código apenas depois de ler todos os valores, pois
                    // o que pode acontecer é de termos um erro na leitura, mas um novo número de código foi criada.
                    print("Qual o nome do produto: ")
                    val name = readln()
                    print("Qual a descricao do produto: ")
                    val description = readln()
                    print("Qual o preco do produto: ")
                    val price = readln().toFloat() // aqui talvez fosse melhor trabalharmos com double
                    print("Qual a quantidade em estoque: ")
                    val amount = readln().toInt()

                    // aqui talvez fosse o melhor lugar para atualizar o itemCode++

                    menu.add(Item(itemCode, name, description, price, amount))
                    println("Item cadastrado com sucesso, codigo: $itemCode")
                }
            }

            2 -> {
                if (menu.isEmpty()) { // não deveria se chamar items a lista de itens cadastrados?
                    println("Nenhum item cadastrado para atualizar.")
                    continue // evite usar o continue de forma desnecessária, aqui seria colocar o else
                }
                // else {}

                //até a leitura disso ficou comprometida com a variável se chamando de menu
                for (item in menu) {
                    // era interessante uma apresentação tabulada
                    println(
                        "Codigo: ${item.code}, " +
                                "Nome: ${item.name}, " +
                                "Descrição: ${item.description}, " +
                                "Preço: ${item.price}, " +
                                "Quantidade em estoque: ${item.amount}"
                    )
                }

                print("Qual o codigo do item que deseja atualizar: ")
                // essa variável poderia se chamar chosenItemCode
                val thisItem = readln().toInt() //aqui a gente pode ter erro na entrada também
                var ishere = false // o nome da variável deveria ser isHere, lembrem-se, variáveis são escritas com
                // camelCase

                /**
                 * aqui era interessante vocês terem feito uma busca no arraylist
                 * usando os campos indexados e o método find
                 *
                 * o retorno de um find feito sobre o withIndex() é um valor que tem:
                 *
                 * index -> indice do valor encontrado na coleção
                 * value -> o valor da posição
                 *
                 *  val itemResult = menu.withIndex().find { it.value.code == thisItem }
                 *                 val index = itemResult?.index
                 *                 val itemRetrieved = itemResult?.value
                 */

                // if(itemResult == null)
                // else

                for (i in 0 until menu.size) {  // beleza, bom uso do until, mas vocês poderiam ter feito de uma forma
                    // um pouco diferente, fazendo um find na lista
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

                // vocês poderiam fazer a comparação antes para verificar se o valor está nulo, assim
                // não precisava entrar no for e pouparia execução
                if (!ishere) {
                    println("O código informado é inválido.")
                }
            }

            3 -> {
                if (menu.isEmpty()) {
                    println("Nenhum item cadastrado, cadastre antes de fazer pedidos.")
                    continue // remover o continue e colocar o else
                }

                // aqui de novo, eu acho que a estratégia deveria ser adicionar quantos itens forem necessários
                // sem que o usuário tenha que saber com antecedência a quantidade
                print("Quantos itens deseja adicionar ao pedido: ")
                val qtd = readln().toInt() // aqui deveria ser amount

                var value = 0.0 //aqui poderia ser total ou orderTotal porque value não representa bem o propósito
                var hasDiscount = false
                val itensOrder: ArrayList<Item> = ArrayList() // o nome deveria ser orderItems, seguindo o padrão de inglês
                orderCode++ // eu acho que o número do pedido deveria ser gerado apenas depois de inserir todos os itens

                for (i in 1..qtd) {

                    // caramba, toda vez que for adicionar um novo item, você vai imprimir todos eles?
                    // isso é ruim para a experiência
                    //talvez fosse legal usar algum print com comando de limpar a tela.
                    for (item in menu) {
                        println(
                            "Codigo: ${item.code}, " +
                                    "Nome: ${item.name}, " +
                                    "Descrição: ${item.description}, " +
                                    "Preço: ${item.price}, " +
                                    "Quantidade em estoque: ${item.amount}"
                        )
                    }

                    // aqui temos aquele probleminha de erro no parse que pode prejudicar todo o processo
                    print("Qual codigo do $i° item que você quer adicionar: ")
                    val code = readln().toInt()

                    // aqui sim temos um uso adequado do find
                    val item = menu.find { it.code == code }
                    if (item != null) {
                        itensOrder.add(item)
                        value += item.price // orderTotal
                    } else {
                        println("O código do item inserido é inválido")
                    }
                }

                print("Você deseja usar um cupom de desconto? (S/N)")
                // esse when deveria servir apenas para definir a porcentagem de desconto, mas tem mais que uma responsabilidade
                when (readln().uppercase()) { // aqui é um bom uso do uppercase, pois padroniza o valor da entrada, mas:
                    // o ideal teria sido você colocar esse valor dentro de uma variável imutável, por exemplo:
                    // val discountOption = readln().uppercase()
                    "S" -> {
                        hasDiscount = true
                        println("Você ganhou 10% de desconto.") // aqui você deveria ter diferenciado a lógica de imprimir
                        // da lógica de aplicar o desconto.
                        value *= 0.90
                    }
                    "N" -> {
                        hasDiscount = false
                        println("Você escolheu não usar o cupom.")
                    }
                    else -> println("Opção inválida") // o que acontece se a opção for inválida?
                    // hoje não acontece nada e definimos que não teve disconto, mas era legal colocar um discount=0.0
                }

                // talvez esse when daqui de cima pudesse ser apenas um if mesmo, não teria problema

                value = Math.round(value * 100) / 100.0 // não entendi o propósito dessa linha, uma vez que você já aplicou o desconto
                // no when

                // isso aqui poderia tá na condição de um do while que ia aceitando inserir novos pedidos
                // enquanto orderItems.isEmpty() || uma opção de não adicionar mais itens fosse escolhida

                if (itensOrder.isEmpty()) {
                    println("Pedido não criado, nenhum item válido selecionado.")
                    continue // esse continue não é uma boa prática de programação
                }

                orders.add(Order(orderCode, Status.ACEITO, value, itensOrder, hasDiscount))
                println("O pedido de código $orderCode foi aceito, valor final: R$ $value")
            }

            4 -> {
                if (orders.isEmpty()) {
                    println("Nenhum pedido cadastrado para atualizar.")
                    continue // substitua esse continue por um else ou escreva o código com lógica reversa
                }

                // sugestão: no lugar do if lá de cima que não faz nada, faça:
                //if( !orders.isEmpty() )
                // e coloque tudo que está aqui embaixo ou coloque no else
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

                // não entendi o propóstivo desse for, ele é para buscar o pedido com o código?
                // mas por que não utilizou o find?
                // e se ia procurar, não era melhor ter separado as lógicas: primeiro eu busco e recupero
                // depois eu pergunto para que status eu quero mudar
                // depois eu mudo?
                // esse código tá muito acoplado as ideias
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
                        break //não é uma boa prática de programação
                    }
                }
                // essa é uma lógica de busca, é, tem que melhorar a separação de tarefas
                if (!ishere) {
                    println("O código informado é inválido.")
                }
            }

            5 -> {
                if (orders.isEmpty()) {
                    println("Nenhum pedido cadastrado.")
                    continue // use o else, evite usar o continue e o break
                }

                println("Filtro de pedidos:")
                // era legal ter feito um forEachIndexed aqui:
                /**
                 *  Status.entries.forEachIndexed{ index, status ->
                 *                     println("${index + 1} - $status")
                 *                 }
                 * assim, vocês não teriam que criar mais um print se fosse adicionado mais um println
                 */
                println(" 1 - TODOS")
                println(" 2 - ACEITO")
                println(" 3 - FAZENDO.")
                println(" 4 - FEITO.")
                println(" 5 - ESPERANDO ENTREGADOR.")
                println(" 6 - SAIU PARA ENTREGA.")
                println(" 7 - ENTREGUE.")

                val status = readln().toInt() // e se tiver erro?
                var filter: Status? = null
                var ishere = false

                // você poderia ter usado o when aqui como expression
                // val statusSelected: Status? = when(status) {
                // fora que a expressão está pouco idiomática
                // além disso, falta o else nesse when, e se o valor for qualquer outro, o que você faz?
                when (status) {
                    2 -> filter = Status.ACEITO
                    3 -> filter = Status.FAZENDO
                    4 -> filter = Status.FEITO
                    5 -> filter = Status.ESPERANDO_ENTREGADOR
                    6 -> filter = Status.SAIU_PARA_ENTREGA
                    7 -> filter = Status.ENTREGUE
                    // e o else?
                }

                // aqui você deveria ter usado o filter
                // o filter ia nos ajudar a retornar vários valores que atendessem a condição
                // sem precisar desse for
                // val filteredOrders: List<Order> = orders.filter { it.status == statusSelected }

                for (order in orders) {
                    if (filter == null || order.status == filter) {
                        println("Codigo: ${order.code}")
                        println("Status: ${order.status}")
                        println("Valor: ${order.value}")
                        println("Itens[ ")
                        // eu acho que isso é complicado, porque vai ter muitos detalhes dos pedidos
                        // aqui era para ter uma estrutura simplificada de visualização dos pedidos
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
                // aqui deveria ser para caso a lista filtrada fosse nulo
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