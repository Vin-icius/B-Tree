public class No {
    public static final int n=3;
    private No vInfo[];
    private No vLig[];
    private int tl;

    public No()
    {
        vInfo = new No[n/2];
        vLig = new No[n/2+1];
        tl=0;
    }

    public No(No info, int posArq)
    {
        this();
        vInfo[0] = info;
        tl = 1;
    }

    public int procurarPosicao(No info)
    {
        int pos=0;
        while(pos<tl && info>vInfo[pos])
            pos++;
        return pos;
    }

    public void remanejar(int pos)
    {
        vLig[tl+1] = vLig[tl];
        for(int i=tl; i>pos; i--)
        {
            vInfo[i] = vInfo[i-1];
            vLig[i] = vLig[i-1];
        }
    }

    public void remanejarExclusao(int pos)
    {
        //////
    }

    public No getvInfo(int p) {
        return vInfo[p];
    }

    public void setvInfo(int p, No info) {
        vInfo[p] = info;
    }

    public No getvLig(int p) {
        return vLig[p];
    }

    public void setvLig(int p, No lig) {
        vLig[p] = lig;
    }

    public int getTl() {
        return tl;
    }

    public void setTl(int tl) {
        this.tl = tl;
    }
}
