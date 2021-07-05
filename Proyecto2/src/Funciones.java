/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author HP15DA0023LA
 */
public class Funciones {
    public Double[][] reColocar(Double[][] matA, int fila1, int fila2,int tamañoM){
        Double [] arreglo= new Double[tamañoM];
        for(int i=0; i<tamañoM;i++)
        {
            arreglo[i]=matA[fila1][i];
            matA[fila1][i]=matA[fila2][i];
            matA[fila2][i]=arreglo[i];
        }
        return matA;
    }
    public void mostrar(Double[][] matA,int filas, int columnas)
    {
        for(int i=0;i<filas;i++)
        {
            for(int j=0;j<columnas;j++)
            {
                System.out.print(matA[i][j]+" # ");
            }
            System.out.println();
        }
    }
    public void mostrarB(Double[] matA)
    {
        for(int i=0;i<3;i++)
        {
                System.out.println(matA[i]);
        }
    }
    public int[] verificarAbajo(Double[][] matA, int pivY, int pivX,int tamañoM) //el Y es el primer corchete de la matriz, el x es el segundo
    {
        int sigAbajo=pivY+1;
        int siHubo=-1;
        int [] arrAux= new int[3];
        while(sigAbajo<tamañoM)
        {
            if(matA[sigAbajo][pivX]!=0)
            {
                siHubo=sigAbajo;
                sigAbajo=tamañoM-1;
            }
            sigAbajo=sigAbajo+1;
        }
        if(siHubo!=-1)
        {
            arrAux[0]=1;    //indica que si hay una fila con la cual se debe intercambiar
            arrAux[1]=pivY; //es el numero de la fila que tenía el cero
            arrAux[2]=siHubo; // es el numero de fila que tenía un numero distinto a cero (para que haga de pivote)
        }
        else
        {
            arrAux[0]=0;
            arrAux[1]=-1; //solo pa que se viera chido
            arrAux[2]=-1; //igual que el de arriba
        }
        return arrAux;
    }
    public Double[][] CeroColumna(Double[][] matA, int pivY, int pivX,int tamaño) //deja en cero los valores debajo del pivote
    {
        Double a=matA[pivY][pivX];
        int x=(pivY+1);
        for(int i=x;i<tamaño;i++)
        {
            Double b=matA[i][pivX];
            Double factor=-b/a;
            for(int j=0;j<tamaño;j++)
            {
                matA[i][j]=(matA[pivY][j]*factor) + matA[i][j];
            }
        }
        return matA;
    }
    //aquí ya vienen de regreso
    public Double[][] dejarEnUno(Double[][] matA, int pivY, int pivX,int tamañoM) //deja en 1 el pivote //Y es la fila
    {
        Double factor= 1/matA[pivY][pivX];
        for(int i=pivX;i<tamañoM;i++)
        {
            matA[pivY][i]=matA[pivY][i]*factor;
        }
        return matA;
    }
    public Double[][] CeroArriba(Double[][] matA, int pivY, int pivX,int tamañoM)
    {
        Double a=matA[pivY][pivX];
        int x=(pivY-1);
        for(int i=x;i>-1;i--)
        {
            Double b=matA[i][pivX];
            Double factor=-b/a;
            for(int j=0;j<tamañoM;j++) //para lo de las columnas
            {
                matA[i][j]=(matA[pivY][j]*factor) + matA[i][j];
            }
        }
        return matA;
    }
    public Double[] multiplicarB(Double[][] matI, Double [] b) //le envió la inversa y el b, me devuelve x 
    {
        //esto creo que no lo voy a usar para el proyecto 2
        Double [] x = new Double[3];
        for(int i=0; i<3;i++)
        {
            Double suma=0.0;
            for(int j=0;j<3;j++)
            {
                suma=(matI[i][j]*b[j])+suma;
            }
            x[i]=suma;
        }
        return x;
    }
    public Double[][] recorrido1(Double[][] matX,int tamañoM)
    {
        int x=0,y=0;
        while(y<tamañoM && x<tamañoM)
        {
            if(matX[x][y]==0) //si es cero el pivote o no lo es
            {
                int [] arreg=this.verificarAbajo(matX, y, x,tamañoM);
                if(arreg[0]==1) //significa que al menos uno de los de abajo no es cero
                {
                    matX=this.reColocar(matX, arreg[1], arreg[2],tamañoM);
                    //#########  AHORA AQUÍ DEBO HACER EL MISMO PROCESO QUE CON LOS PIVOTES NO CERO (porque ahora el pivote ya no es cero)
                    matX=this.CeroColumna(matX, y, x,tamañoM);
                }
                else //muevase al lado
                {
                    y=y-1; //para que no se mueva de esa fila, solo se mueva de columna al llegar al final de esta iteración del while
                }
            }
            else    //si no era cero el pivote, aquí tiene que comenzar el algoritmo de bajada
            {
                matX=this.CeroColumna(matX, y, x,tamañoM);
            }
            x=x+1;
            y=y+1;
        }
        return matX;
    }
    public Double[][] recorrido2(Double[][] matX,int tamañoM)
    {
        for(int i=(tamañoM-1); i>-1;i--)
        {
            int[] posPiv= new int[2];
            posPiv=this.buscarPivote(matX, tamañoM,i);
            if(posPiv[0]==1) //esto para saber si al menos tiene un pivote
            {
                if(matX[i][posPiv[1]]!=1)
                {
                    matX=this.dejarEnUno(matX, i, posPiv[1],tamañoM);
                }
                matX=this.CeroArriba(matX, i, posPiv[1],tamañoM);
            } //si no tiene no importa porque igual se checará con las demás filas
        }
        return matX;
    }
    private int[] buscarPivote(Double[][] matX,int tamañoM, int fila)
    {
        int[] x= new int[2]; //primera posición: Si/NO (1/0) Segunda: Numero de columna donde estaba
        x[0]=0;
        x[1]=0;
        int contador=0;
        while(contador<tamañoM)
        {
            if(matX[fila][contador]!=0)
            {
                x[0]=1;
                x[1]=contador;
                return x;
            }
            contador++;
        }
        return x;
    }
            
    
    public Double[][] recorridoDeterminantes(Double[][] matX,int tope,int tamañoM)
    {
        int x=0,y=0;
        while(y!=2)
        {
            if(matX[x][y]==0) //si es cero el pivote o no lo es
            {
                int [] arreg=this.verificarAbajo(matX, y, x,tamañoM);
                if(arreg[0]==1) //significa que al menos uno de los de abajo no es cero
                {
                    matX=this.reColocar(matX, arreg[1], arreg[2],tamañoM);
                    //#########  AHORA AQUÍ DEBO HACER EL MISMO PROCESO QUE CON LOS PIVOTES NO CERO (porque ahora el pivote ya no es cero)
                    matX=this.CeroColumna(matX, y, x,tamañoM);
                }
                else //muevase al lado
                {
                    y=y-1; //para que no se mueva de esa fila, solo se mueva de columna al llegar al final de esta iteración del while
                }
            }
            else    //si no era cero el pivote, aquí tiene que comenzar el algoritmo de bajada
            {
                matX=this.CeroColumna(matX, y, x,tamañoM);
            }
            x=x+1;
            y=y+1;
        }
        return matX;
    }
    public double calcularDeter(Double[][] matX)
    {
        double valor=0.0;
        valor= matX[2][2]*matX[3][3] - matX[3][2]*matX[2][3];
        valor=valor*matX[0][0]*matX[1][1];
        return valor;
    }
}
