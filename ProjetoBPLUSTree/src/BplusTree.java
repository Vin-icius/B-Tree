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

    private void split(No folha, No pai)
    {
        No cx1 = new No();
        No cx2 = new No();
        for(int i=0; i<No.m; i++)
        {
            cx1.setvInfo(i, folha.getvInfo(i));
            cx1.setvPos(i, folha.getvPos(i));
            cx1.setvLig(i, folha.getvLig(i));
        }
        cx1.setvLig(No.m, folha.getvLig(No.m));
        cx1.setTl(No.m);

        for(int i=No.m+1; i<No.m*2+1; i++)
        {
            cx2.setvInfo(i-(No.m+1), folha.getvInfo(i));
            cx2.setvPos(i-(No.m+1), folha.getvPos(i));
            cx2.setvLig(i-(No.m+1), folha.getvLig(i));
        }
        cx2.setvLig(No.m, folha.getvLig(No.m*2+1));
        cx2.setTl(No.m);

        if(folha == pai)
        {
            folha.setvInfo(0, folha.getvInfo(No.m));
            folha.setvPos(0, folha.getvPos(No.m));
            folha.setTl(1);
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
        }
        else
        {
            int pos = pai.procurarPosicao(folha.getvInfo(No.m));
            pai.remanejar(pos);
            pai.setvInfo(pos,folha.getvInfo(No.m));
            pai.setvPos(pos,folha.getvPos(No.m));
            pai.setTl(pai.getTl()+1);
            pai.setvLig(pos,cx1);
            pai.setvLig(pos+1,cx2);
            if(pai.getTl()>2*No.m)
            {
                folha = pai;
                pai = localizarPai(folha,folha.getvInfo(0));
                split(folha,pai);
            }
        }
    }

    public void inserir(int info, int posArq)
    {
        No folha,pai;
        int pos;
        if(raiz == null)
            raiz = new No(info,posArq);
        else
        {
            folha = navegarAteFolha(info);
            pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setvPos(pos, posArq);
            folha.setTl(folha.getTl()+1);
            if(folha.getTl() > 2*No.m)
            {
                pai = localizarPai(folha, info);
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

        if(irmaE!=null && irmaE.getTl()>No.m)
        {
            folha.remanejar(0);
            folha.setvInfo(0, pai.getvInfo(posPai-1));
            folha.setvPos(0, pai.getvPos(posPai-1));
            folha.setTl(folha.getTl()+1);
            pai.setvInfo(posPai-1, irmaE.getvInfo(irmaE.getTl()-1));
            pai.setvPos(posPai-1, irmaE.getvPos(irmaE.getTl()-1));
            folha.setvLig(0, irmaE.getvLig(irmaE.getTl()));
            irmaE.setTl(irmaE.getTl()-1);
        }
        else
        if(irmaD!=null && irmaD.getTl()>No.m)
        {

        }
        else //concatenacao
        {
            if(irmaE!=null)
            {
                irmaE.setvInfo(irmaE.getTl(), pai.getvInfo(posPai-1));
                irmaE.setvPos(irmaE.getTl(), pai.getvPos(posPai-1));
                irmaE.setTl(irmaE.getTl()+1);
                pai.remanejarExclusao(posPai-1);
                pai.setTl(pai.getTl()-1);
                pai.setvLig(posPai-1, irmaE);
                for(int i=0; i<folha.getTl(); i++)
                {
                    irmaE.setvInfo(irmaE.getTl(), folha.getvInfo(i));
                    irmaE.setvPos(irmaE.getTl(), folha.getvPos(i));
                    irmaE.setvLig(irmaE.getTl(), folha.getvLig(i));
                    irmaE.setTl(irmaE.getTl()+1);
                }
                irmaE.setvLig(irmaE.getTl(), folha.getvLig(folha.getTl()));
            }
            else
            {

            }

            if(pai==raiz && pai.getTl()==0)
            {
                if(irmaE!=null)
                    raiz=irmaE;
                else
                    raiz=irmaD;
            }
            else
            if(pai!=raiz && pai.getTl()<No.m)
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
                if(subE.getTl()>No.m || subD.getTl()==No.m)
                {
                    no.setvInfo(pos, subE.getvInfo(subE.getTl()-1));
                    no.setvPos(pos, subE.getvPos(subE.getTl()-1));
                    folha=subE;
                    pos=subE.getTl()-1;
                }
                else
                {
                    no.setvInfo(pos, subD.getvInfo(0));
                    no.setvPos(pos, subD.getvPos(0));
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
            if(raiz!=folha && folha.getTl()<No.m)
                redistribuir_concatenar(folha);
        }
    }
}