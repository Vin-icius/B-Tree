public class Main {
    public static void main(String[] args) {
        BplusTree b = new BplusTree();

        for(int i=1; i<=100; i++)
            b.inserir(i, 0);

        System.out.println("Terminou!!!");
        b.in_ordem();
    }
}
