import java.math.*;
public class BplusTree {
    private No raiz;

    public BplusTree()
    {
        raiz = null;
    }

    private No navegarAteFolha(int info)
    {
        int pos;
        No no=raiz;
        while(no.getvLig(0)!=null)
        {
            pos=no.procurarPosicao(info);
            no = no.getvLig(pos);
        }
        return no;
    }

    public void setOrdem(int n){
        No.n=n;
    }
    private boolean isFolha(No nodo){
        return nodo.getvLig(0)==null;
    }

    private No localizarPai(No folha, int info)
    {
        int pos;
        No no,pai;
        no=pai=raiz;
        while(no!=folha)
        {
            pai=no;
            pos=no.procurarPosicao(info);
            no = no.getvLig(pos);
        }
        return pai;
    }

    public void exibirFolhas(No raiz) {
        if (raiz == null) {
            return;
        }

        // Descer até a primeira folha
        No folha = raiz;
        while (folha.getvLig(0) != null) {
            folha = folha.getvLig(0);
        }

        // Percorrer as folhas a partir da primeira folha
        while (folha != null) {
            for (int i = 0; i < folha.getTl(); i++) {
                System.out.print(folha.getvInfo(i) + " ");
            }
            System.out.println(); // \n
            folha = folha.getProx();
        }
    }

    public void inserir(int info, int posArq)
    {
        No folha, pai;
        int pos;

        if(raiz==null){
            raiz = new No(info,posArq);
        }
        else{
            folha = navegarAteFolha(info);
            pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos,info);
            folha.setTl(folha.getTl()+1);
            if(folha.getTl()==No.n){
                pai = localizarPai(folha,info);
                split(folha,pai);
            }

            No novaFolha = folha;
            No folhaAnterior = folha.getAnt();
            if (folhaAnterior != null) {
                folhaAnterior.setProx(novaFolha);
                novaFolha.setAnt(folhaAnterior);
            }
            folha.setAnt(folhaAnterior);
            folha.setProx(null);

        }
    }

    private void split(No folha, No pai)
    {
        No cx1 = new No();
        No cx2 = new No();
        int n = No.n; // No.n é uma variável estática
        int i=0;
        int mid = (int) Math.ceil((double) n / 2);
        int mid2 = (int) Math.ceil((double) n / 2) - 1;
        int mid3 = (int) Math.ceil((double) (n - 1) / 2);

        if (isFolha(folha)) {
            for (i = 0; i < mid3; i++) {
                cx1.setvInfo(i, folha.getvInfo(i));
                cx1.setTl(cx1.getTl() + 1);
            }

            for (i = mid3; i < n; i++) {
                cx2.setvInfo(i - mid3, folha.getvInfo(i));
                cx2.setTl(cx2.getTl() + 1);
            }
        }
        else {
            for (i = 0; i < mid; i++) {
                cx1.setvInfo(i,folha.getvInfo(i));
                cx1.setvLig(i,folha.getvLig(i));
                cx1.setTl(cx1.getTl() + 1);
            }
            cx1.setvLig(mid,folha.getvLig(mid));
            mid2 = mid++;
            for (i = mid; i < n; i++) {
                cx2.setvInfo(i - (mid),folha.getvInfo(i));
                cx2.setvLig(i - (mid), folha.getvLig(i));
                cx2.setTl(cx2.getTl() + 1);
            }
            cx2.setvLig(i - mid, folha.getvLig(n));
        }
        if (folha == pai) {
            folha.setvInfo(0, cx2.getvInfo(0));
            folha.setTl(1);
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
        }
        else {
            int pos = pai.procurarPosicao(folha.getvInfo(mid));
            pai.remanejar(pos);
            pai.setvInfo(pos, folha.getvInfo(mid));
            pai.setTl(pai.getTl() + 1);
            pai.setvLig(pos, cx1);
            pai.setvLig(pos + 1, cx2);
            if (pai.getvLig(0).getvLig(0) == null) {
                for (int j = 0; j < pai.getTl(); j++) {
                    pai.getvLig(j).setProx(pai.getvLig(j + 1));
                    pai.getvLig(j + 1).setAnt(pai.getvLig(j));
                }
                pai.getvLig(pai.getTl()).setAnt(pai.getvLig(pai.getTl() - 1));
            }
            if (pai.getTl() > n - 1) {
                folha = pai;
                pai = localizarPai(folha, folha.getvInfo(mid));
                split(folha, pai);
            }
        }
    }

    public void in_ordem()
    {
        in_ordem(raiz);
    }

    private void in_ordem(No no)
    {
        if(no!=null)
        {
            for(int i=0; i<no.getTl(); i++)
            {
                in_ordem(no.getvLig(i));
                System.out.println(no.getvInfo(i));
            }
            in_ordem(no.getvLig(no.getTl()));
        }
    }

    private No localizarNo(int info)
    {
        int pos;
        No no=raiz;
        boolean flag=false;
        while(no!=null && !flag)
        {
            pos = no.procurarPosicao(info);
            if (pos<no.getTl() && info==no.getvInfo(pos))
                flag=true;
            else
                no = no.getvLig(pos);
        }
        return no;
    }

    private No localizarSubE(No no, int pos)
    {
        no = no.getvLig(pos);
        while(no.getvLig(0)!=null)
            no = no.getvLig(no.getTl());
        return no;
    }

    private No localizarSubD(No no, int pos)
    {
        no = no.getvLig(pos);
        while(no.getvLig(0)!=null)
            no = no.getvLig(0);
        return no;
    }

    private void redistribuir_concatenar(No folha)
    {
        No pai = localizarPai(folha, folha.getvInfo(0));
        No irmaE=null, irmaD=null;
        int posPai = pai.procurarPosicao(folha.getvInfo(0));
        if(posPai>0)
            irmaE = pai.getvLig(posPai-1);
        if(posPai+1<=pai.getTl())
            irmaD = pai.getvLig(posPai+1);

        if(irmaE!=null && irmaE.getTl()>No.n)
        {
            folha.remanejar(0);
            folha.setvInfo(0, pai.getvInfo(posPai-1));
            folha.setTl(folha.getTl()+1);
            pai.setvInfo(posPai-1, irmaE.getvInfo(irmaE.getTl()-1));
            folha.setvLig(0, irmaE.getvLig(irmaE.getTl()));
            irmaE.setTl(irmaE.getTl()-1);
        }
        else
        if(irmaD!=null && irmaD.getTl()>No.n)
        {
            folha.setvInfo(folha.getTl(), pai.getvInfo(posPai));
            folha.setTl(folha.getTl() + 1);
            pai.setvInfo(posPai, irmaD.getvInfo(0));

            for (int i = 1; i < irmaD.getTl(); i++) {
                irmaD.setvInfo(i - 1, irmaD.getvInfo(i));
                irmaD.setvLig(i - 1, irmaD.getvLig(i));
            }
            irmaD.setvLig(irmaD.getTl() - 1, irmaD.getvLig(irmaD.getTl()));
            irmaD.setTl(irmaD.getTl() - 1);
        }
        else //concatenacao
        {
            if(irmaE!=null)
            {
                irmaE.setvInfo(irmaE.getTl(), pai.getvInfo(posPai-1));
                irmaE.setTl(irmaE.getTl()+1);
                pai.remanejarExclusao(posPai-1);
                pai.setTl(pai.getTl()-1);
                pai.setvLig(posPai-1, irmaE);
                for(int i=0; i<folha.getTl(); i++)
                {
                    irmaE.setvInfo(irmaE.getTl(), folha.getvInfo(i));
                    irmaE.setvLig(irmaE.getTl(), folha.getvLig(i));
                    irmaE.setTl(irmaE.getTl()+1);
                }
                irmaE.setvLig(irmaE.getTl(), folha.getvLig(folha.getTl()));
            }
            else
            {
                folha.setvInfo(folha.getTl(), pai.getvInfo(posPai));
                folha.setTl(folha.getTl() + 1);
                pai.remanejarExclusao(posPai);
                pai.setTl(pai.getTl() - 1);
                pai.setvLig(posPai, folha);
                for (int i = 0; i < irmaD.getTl(); i++)
                {
                    folha.setvInfo(folha.getTl(), irmaD.getvInfo(i));
                    folha.setvLig(folha.getTl(), irmaD.getvLig(i));
                    folha.setTl(folha.getTl() + 1);
                }
                folha.setvLig(folha.getTl(), irmaD.getvLig(irmaD.getTl()));
            }

            if(pai==raiz && pai.getTl()==0)
            {
                if(irmaE!=null)
                    raiz=irmaE;
                else
                    raiz=irmaD;
            }
            else
            if(pai!=raiz && pai.getTl()<No.n)
            {
                folha = pai;
                redistribuir_concatenar(folha);
            }

        }
    }

    public void excluir(int info)
    {
        int pos;
        No subE, subD, folha, pai;
        No no = localizarNo(info);
        if(no!=null)
        {
            pos = no.procurarPosicao(info);
            //verifica se não é folha
            if(no.getvLig(0)!=null)
            {
                subE = localizarSubE(no, pos);
                subD = localizarSubD(no, pos+1);
                if(subE.getTl()>No.n || subD.getTl()==No.n)
                {
                    no.setvInfo(pos, subE.getvInfo(subE.getTl()-1));
                    folha=subE;
                    pos=subE.getTl()-1;
                }
                else
                {
                    no.setvInfo(pos, subD.getvInfo(0));
                    folha=subD;
                    pos=0;
                }
            }
            else
                folha=no;

            folha.remanejarExclusao(pos);
            folha.setTl(folha.getTl()-1);

            if(raiz==folha && folha.getTl()==0)
                raiz = null;
            else
            if(raiz!=folha && folha.getTl()<No.n)
                redistribuir_concatenar(folha);
        }
    }
}
