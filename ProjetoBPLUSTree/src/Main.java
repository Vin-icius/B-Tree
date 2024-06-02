public class Main {
    public static void main(String[] args) {
        BplusTree b = new BplusTree();

        for(int i=1; i<=10; i++)
            b.inserir(i, 0);

        System.out.println("Terminou!!!");
        b.in_ordem();

        b.excluir(5);

        b.in_ordem();

        BplusTree b2 = new BplusTree();

        b2.setOrdem(5);
        for(int i=1; i<=10; i++)
            b2.inserir(i, 0);

        System.out.println("Terminou!!!");
        b2.in_ordem();

        b2.excluir(5);

        b2.in_ordem();
    }
}
