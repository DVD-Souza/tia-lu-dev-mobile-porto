fun main(){
    var option: Int;

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
            1 -> print("")//logica do cadstro de item
            2 -> print("")//logica da atualização de item
            3 -> print("")//logica do cadastro de pedidos
            4 -> print("")//logica da atualização de pedidos
            5 -> print("")//consulta de pedidos
            0 -> print("ENCERRANDO O SISTEMA")
        }

    }while (option != 0)
}