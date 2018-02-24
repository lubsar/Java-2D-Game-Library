package svk.sglubos.oengine.lib.math;

public class Mat2f {
	public float a;
	public float b;
	public float c;
	public float d;
	
	public Mat2f() {
		this(1.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public Mat2f(float a, float b, float c, float d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public void multiply(Mat2f mat) {
		float a = this.a * mat.a + this.b * mat.c;
		float b = this.a * mat.b + this.b * mat.d;
		float c = this.c * mat.a + this.d * mat.b;
		float d = this.c * mat.b + this.d * mat.d;
		
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public void add(Mat2f mat) {
		this.a += mat.a;
		this.b += mat.b;
		this.c += mat.c;
		this.d += mat.d;
	}
	
	public void subtract(Mat2f mat) {
		this.a -= mat.a;
		this.b -= mat.b;
		this.c -= mat.c;
		this.d -= mat.d;
	}
	
	public void scale(float scale) {
		this.a *= scale;
		this.b *= scale;
		this.c *= scale;
		this.d *= scale;
	}
	
	public void identity() {
		this.a = 1.0f;
		this.b = 0.0f;
		this.c = 0.0f;
		this.d = 1.0f;
	}
	
	public static Mat2f add(Mat2f mat1, Mat2f mat2) {
		return new Mat2f(mat1.a + mat2.a, mat1.b + mat2.b, mat1.c + mat2.c, mat1.d + mat2.d);
	}
	
	public static Mat2f subtract(Mat2f mat1, Mat2f mat2) {
		return new Mat2f(mat1.a - mat2.a, mat1.b - mat2.b, mat1.c - mat2.c, mat1.d - mat2.d);
	}
	
	public static Mat2f scale(Mat2f mat, float scale) {
		return new Mat2f(mat.a * scale, mat.b * scale, mat.c * scale, mat.d * scale);
	}
	
	public static Mat2f multiply(Mat2f mat1, Mat2f mat2) {
		float a = mat1.a * mat2.a + mat1.b * mat2.c;
		float b = mat1.a * mat2.b + mat1.b * mat2.d;
		float c = mat1.c * mat2.a + mat1.d * mat2.b;
		float d = mat1.c * mat2.b + mat1.d * mat2.d;
		
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
