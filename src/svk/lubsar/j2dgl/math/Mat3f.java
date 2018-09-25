package svk.sglubos.oengine.lib.math;

public class Mat3f {
	public float[] mat = new float[9];
	
	public Mat3f() {
		this(1.0f, 0.0f, 0.0f,
			 0.0f, 1.0f, 0.0f,
			 0.0f, 0.0f, 1.0f);
	}
	
	public Mat3f(float[] mat) {
		this(mat[0], mat[1], mat[2],
			 mat[3], mat[4], mat[5],
			 mat[6], mat[7], mat[8]);
		
		if(mat.length != 9) {
			throw new RuntimeException("Length of the array is not 9! " + mat.length);
		}	
	}
	
	public Mat3f(float a, float b, float c,
				 float d, float e, float f,
				 float g, float h, float i) {
		mat[0] = a;
		mat[1] = b;
		mat[2] = c;
		
		mat[3] = d;
		mat[4] = e;
		mat[5] = f;
		
		mat[6] = g;
		mat[7] = h;
		mat[8] = i;
	}
	
	public void multiply(Mat3f mat) {
		float a = this.mat[0] * mat.mat[0] + this.mat[1] * mat.mat[3] + this.mat[2] * mat.mat[6];
		float b = this.mat[0] * mat.mat[1] + this.mat[1] * mat.mat[4] + this.mat[2] * mat.mat[7];
		float c = this.mat[0] * mat.mat[2] + this.mat[1] * mat.mat[5] + this.mat[2] * mat.mat[8];
		
		float d = this.mat[3] * mat.mat[0] + this.mat[4] * mat.mat[3] + this.mat[5] * mat.mat[6];
		float e = this.mat[3] * mat.mat[1] + this.mat[4] * mat.mat[3] + this.mat[5] * mat.mat[7];
		float f = this.mat[3] * mat.mat[2] + this.mat[4] * mat.mat[5] + this.mat[5] * mat.mat[8];
		
		float g = this.mat[6] * mat.mat[0] + this.mat[7] * mat.mat[3] + this.mat[8] * mat.mat[6];
		float h = this.mat[6] * mat.mat[1] + this.mat[7] * mat.mat[3] + this.mat[8] * mat.mat[7];
		float i = this.mat[6] * mat.mat[2] + this.mat[7] * mat.mat[5] + this.mat[8] * mat.mat[8];

		
		this.mat[0] = a;
		this.mat[1] = b;
		this.mat[2] = c;
		
		this.mat[3] = d;
		this.mat[4] = e;
		this.mat[5] = f;
		
		this.mat[6] = g;
		this.mat[7] = h;
		this.mat[8] = i;
	}
	
	public void add(Mat2f mat) {
		this.mat[0] += mat.mat[0];
		this.mat[1] += mat.mat[1];
		this.mat[2] += mat.mat[2];
		                          
		this.mat[3] += mat.mat[3];
		this.mat[4] += mat.mat[4];
		this.mat[5] += mat.mat[5];
		                          
		this.mat[6] += mat.mat[6];
		this.mat[7] += mat.mat[7];
		this.mat[8] += mat.mat[8];
	}
	
	public void subtract(Mat2f mat) {
		this.mat[0] -= mat.mat[0];
		this.mat[1] -= mat.mat[1];
		this.mat[2] -= mat.mat[2];
		                         
		this.mat[3] -= mat.mat[3];
		this.mat[4] -= mat.mat[4];
		this.mat[5] -= mat.mat[5];
		                        
		this.mat[6] -= mat.mat[6];
		this.mat[7] -= mat.mat[7];
		this.mat[8] -= mat.mat[8];
	}
	
	public void scale(float scale) {
		this.mat[0] *= scale;
		this.mat[1] *= scale;
		this.mat[2] *= scale;
		            
		this.mat[3] *= scale;
		this.mat[4] *= scale;
		this.mat[5] *= scale;
		            
		this.mat[6] *= scale;
		this.mat[7] *= scale;
		this.mat[8] *= scale;
	}
	
	public void identity() {
		this.mat[0] = 1.0f;
		this.mat[1] = 0.0f;
		this.mat[2] = 0.0f;
		            
		this.mat[3] = 0.0f;
		this.mat[4] = 1.0f;
		this.mat[5] = 0.0f;
		            
		this.mat[6] = 0.0f;
		this.mat[7] = 0.0f;
		this.mat[8] = 1.0f;
	}
	
	public static Mat3f add(Mat3f mat1, Mat3f mat2) {
		return new Mat3f(mat1.mat[0] + mat2.mat[0], mat1.mat[1] + mat2.mat[1], mat1.mat[2] + mat2.mat[2],
						 mat1.mat[3] + mat2.mat[3], mat1.mat[4] + mat2.mat[4], mat1.mat[5] + mat2.mat[5],
						 mat1.mat[6] + mat2.mat[6], mat1.mat[7] + mat2.mat[7], mat1.mat[8] + mat2.mat[8]);
	}
		
	public static Mat3f subtract(Mat3f mat1, Mat3f mat2) {
		return new Mat3f(mat1.mat[0] - mat2.mat[0], mat1.mat[1] - mat2.mat[1], mat1.mat[2] - mat2.mat[2],
						 mat1.mat[3] - mat2.mat[3], mat1.mat[4] - mat2.mat[4], mat1.mat[5] - mat2.mat[5],
						 mat1.mat[6] - mat2.mat[6], mat1.mat[7] - mat2.mat[7], mat1.mat[8] - mat2.mat[8]);
	}
	
	public static Mat3f scale(Mat3f mat, float scale) {
		return new Mat3f(mat.mat[0] * scale, mat.mat[1] * scale, mat.mat[2] * scale, 
						 mat.mat[3] * scale, mat.mat[4] * scale, mat.mat[5] * scale, 
						 mat.mat[6] * scale, mat.mat[7] * scale, mat.mat[8] * scale);
	}
	
	public static Mat3f multiply(Mat3f mat1, Mat3f mat2) {
		float a = mat1.mat[0] * mat2.mat[0] + mat1.mat[1] * mat2.mat[3] + mat1.mat[2] * mat2.mat[6];
		float b = mat1.mat[0] * mat2.mat[1] + mat1.mat[1] * mat2.mat[4] + mat1.mat[2] * mat2.mat[7];
		float c = mat1.mat[0] * mat2.mat[2] + mat1.mat[1] * mat2.mat[5] + mat1.mat[2] * mat2.mat[8];
		
		float d = mat1.mat[3] * mat2.mat[0] + mat1.mat[4] * mat2.mat[3] + mat1.mat[5] * mat2.mat[6];
		float e = mat1.mat[3] * mat2.mat[1] + mat1.mat[4] * mat2.mat[3] + mat1.mat[5] * mat2.mat[7];
		float f = mat1.mat[3] * mat2.mat[2] + mat1.mat[4] * mat2.mat[5] + mat1.mat[5] * mat2.mat[8];
		
		float g = mat1.mat[6] * mat2.mat[0] + mat1.mat[7] * mat2.mat[3] + mat1.mat[8] * mat2.mat[6];
		float h = mat1.mat[6] * mat2.mat[1] + mat1.mat[7] * mat2.mat[3] + mat1.mat[8] * mat2.mat[7];
		float i = mat1.mat[6] * mat2.mat[2] + mat1.mat[7] * mat2.mat[5] + mat1.mat[8] * mat2.mat[8];

		
		return new Mat3f(a, b, c, 
						 d, e, f,
						 g, h, i);
	}
	
	public static Mat3f indentity() {
		return new Mat3f();
	}
	
	public static Mat3f rotation(float angleDeg, boolean clockwise) {
		double angle = Math.toRadians(angleDeg);
		
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		
		return new Mat3f(cos, clockwise ? sin: - sin, 0.0f, 
				         clockwise ? -sin : sin, cos, 0.0f,
				         0.0f, 0.0f, 1.0f);
	}
	
	public static Mat3f scale(float xscale, float yscale) {
		return new Mat3f(xscale, 0.0f, 0.0f,
				         0.0f, yscale, 0.0f,
				         0.0f, 0.0f, 1.0f);
	}
}
