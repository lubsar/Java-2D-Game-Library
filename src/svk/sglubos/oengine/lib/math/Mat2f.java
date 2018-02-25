package svk.sglubos.oengine.lib.math;

public class Mat2f {
	public float[] mat = new float[4];
	
	public Mat2f() {
		this(1.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public Mat2f(float[] mat) {
		this(mat[0], mat[1], mat[2], mat[3]);
		
		if(mat.length != 4) {
			throw new RuntimeException("Length of the array is not 4! " + mat.length);
		}	
	}
	
	public Mat2f(float a, float b, float c, float d) {
		mat[0] = a;
		mat[1] = b;
		mat[2] = c;
		mat[3] = d;
	}
	
	public void multiply(Mat2f mat) {
		float a = this.mat[0] * mat.mat[0] + this.mat[1] * mat.mat[2];
		float b = this.mat[0] * mat.mat[1] + this.mat[1] * mat.mat[3];
		float c = this.mat[2] * mat.mat[0] + this.mat[3] * mat.mat[2];
		float d = this.mat[2] * mat.mat[1] + this.mat[3] * mat.mat[3];

		
		this.mat[0] = a;
		this.mat[1] = b;
		this.mat[2] = c;
		this.mat[3] = d;
	}
	
	public void add(Mat2f mat) {
		this.mat[0] += mat.mat[0];
		this.mat[1] += mat.mat[1];
		this.mat[2] += mat.mat[2];
		this.mat[3] += mat.mat[3];
	}
	
	public void subtract(Mat2f mat) {
		this.mat[0] -= mat.mat[0];
		this.mat[1] -= mat.mat[1];
		this.mat[2] -= mat.mat[2];
		this.mat[3] -= mat.mat[3];
	}
	
	public void scale(float scale) {
		this.mat[0] *= scale;
		this.mat[1] *= scale;
		this.mat[2] *= scale;
		this.mat[3] *= scale;
	}
	
	public void identity() {
		this.mat[0] = 1.0f;
		this.mat[1] = 0.0f;
		this.mat[2] = 0.0f;
		this.mat[3] = 1.0f;
	}
	
	public static Mat2f add(Mat2f mat1, Mat2f mat2) {
		return new Mat2f(mat1.mat[0] + mat2.mat[0], mat1.mat[1] + mat2.mat[1], mat1.mat[2] + mat2.mat[2], mat1.mat[3] + mat2.mat[3]);
	}
		
	public static Mat2f subtract(Mat2f mat1, Mat2f mat2) {
		return new Mat2f(mat1.mat[0] - mat2.mat[0], mat1.mat[1] - mat2.mat[1], mat1.mat[2] - mat2.mat[2], mat1.mat[3] - mat2.mat[3]);
	}
	
	public static Mat2f scale(Mat2f mat, float scale) {
		return new Mat2f(mat.mat[0] * scale, mat.mat[1] * scale, mat.mat[2] * scale, mat.mat[3] * scale);
	}
	
	public static Mat2f multiply(Mat2f mat1, Mat2f mat2) {
		float a = mat1.mat[0] * mat2.mat[0] + mat1.mat[1] * mat2.mat[2];
		float b = mat1.mat[0] * mat2.mat[1] + mat1.mat[1] * mat2.mat[3];
		float c = mat1.mat[2] * mat2.mat[0] + mat1.mat[3] * mat2.mat[1];
		float d = mat1.mat[2] * mat2.mat[1] + mat1.mat[3] * mat2.mat[3];
		
		return new Mat2f(a, b, c ,d);
	}
	
	public static Mat2f indentity() {
		return new Mat2f();
	}
	
	public static Mat2f rotation(float angleDeg, boolean clockwise) {
		double angle = Math.toRadians(angleDeg);
		
		float sin = (float) Math.sin(angle);
		float cos = (float) Math.cos(angle);
		
		return new Mat2f(cos, clockwise ? sin: - sin, clockwise ? -sin : sin, cos);
	}
	
	public static Mat2f scale(float xscale, float yscale) {
		return new Mat2f(xscale, 0.0f, 0.0f, yscale);
	}
}
