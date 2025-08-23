data class Item(
    val code: Int,
    val name: String,
    val description: String,
    val price: Float,
    val amount: Int
)

data class Order(
    val code: Int,
    val status: String,
    val value: Float,
    val itens: List<Item>,
    val discount: Float
)


fun main(){
    var option: Int
    var menu : ArrayList<Item> = ArrayList()
    var itemCode: Int = 0

    do{
        println("==================================================");
        println("                    MENU                          ");
        println("Opção 1: CADASTRAR ITEM");
        println("Opção 2: ATUALIZAR ITEM")
        println("OPÇÂO 3: CRIAR PEDIDO")
        println("Opção 4: ATUALIZAR PEDIDO")
        println("Opção 5: CONSULTAR PEDIDO(S)")
        println("Opção 0: ENCERRAR O SISTEMA")
        println("==================================================");

        option = readln().toInt()

        when(option){
            1 -> {
                itemCode++
                print("Qual o nome do produto?")
                val name = readln()
                print("Qual a descricao do produto?")
                val description = readln()
                print("Qual o preco do produto?")
                val price = readln().toFloat()
                print("Qual a quantidade?")
                val amount = readln().toInt()
                menu.add(Item(itemCode,name,description,price,amount))
            }//logica do cadastro de item
            2 -> {
                print("Qual o codigo do item?")
                val thisItem = readln().toInt()
                print("Qual o nome do produto?")
                val name = readln()
                print("Qual a descricao do produto?")
                val description = readln()
                print("Qual o preco do produto?")
                val price = readln().toFloat()
                print("Qual a quantidade?")
                val amount = readln().toInt()
                val newItem = Item( thisItem,name,description,price,amount)
                for (i in 0 until menu.size){
                   if (menu[i].code == thisItem) {
                       menu[i] = newItem
                       break
                   }
                }
                } //logica da atualização de item

            3 -> {
                print("") //logica do cadastro de pedidos
            }

            4 -> {
                print("") //logica da atualização de pedidos
            }

            5 -> {
                print("") //consulta de pedidos
            }

            0 -> {
                print("ENCERRANDO O SISTEMA")
            }
        }

    }while (option != 0)
}