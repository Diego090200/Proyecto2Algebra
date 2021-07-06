
import Jama.*; 
public class Operaciones {
    public static Matrix mCopia = new Matrix(3,3);
    Matrix m = new Matrix(3,3);   
    
    
    public double getValue(int n1, int n2){
        return mCopia.get(n2, n1);
    }
    public void llenarMatriz(float M1, float M2, float M3,  
            float M4, float M5, float M6,  
            float M7, float M8, float M9
             )
    {
        m.set(0, 0, M1);
        m.set(0, 1, M2);
        m.set(0, 2, M3);

        m.set(1, 0, M4);
        m.set(1, 1, M5);
        m.set(1, 2, M6);

        m.set(2, 0, M7);
        m.set(2, 1, M8);
        m.set(2, 2, M9);

        
        EigenvalueDecomposition eigen = m.eig();

	double [] realPart = eigen.getRealEigenvalues();
	for(int i = 0; i < realPart.length; i++) {
            Vectores.valoresPropios.add(realPart[i]);  
	}
        Matrix evectors = eigen.getV();
        mCopia = evectors;

    }
}
