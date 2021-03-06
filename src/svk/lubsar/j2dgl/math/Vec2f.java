package svk.lubsar.j2dgl.math;

public class Vec2f {
	public float x,y;
	
	public Vec2f() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vec2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void add(Vec2f vec) {
		x = x + vec.x;
		y = y + vec.y;
	}
	
	public void subtract(Vec2f vec) {
		x = x - vec.x;
		y = y - vec.y;
	}
	
	public void scale(float scale) {
		x *= scale;
		y *= scale;
	}
	
	public float dot(Vec2f vec) {
		return x * vec.x + y * vec.y;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y); 
	}
	
	public void normalize() {
		float length = (float) Math.sqrt(x * x + y * y);
		
		x /= length;
		y /= length;
	}
	
	public float distance(Vec2f vec) {
		float x = vec.x - this.x;
		float y = vec.y - this.y;
		return (float) Math.sqrt((x * x) + (y * y));
	}
	
	public void transform(Mat2f matrix) {
		float x = matrix.mat[0] * this.x + matrix.mat[1] * this.y;
		float y = matrix.mat[2] * this.x + matrix.mat[3] * this.y;
		
		this.x = x;
		this.y = y;
	}
	
	public void affineTransform(Mat3f matrix) {
		float x = matrix.mat[0] * this.x + matrix.mat[1] * this.y + matrix.mat[2];
		float y = matrix.mat[3] * this.x + matrix.mat[4] * this.y + matrix.mat[5];
		
		this.x = x;
		this.y = y;
	}
	
	public static float distance(Vec2f vec1, Vec2f vec2) {
		float x = vec1.x - vec2.x;
		float y = vec1.y - vec2.y;
		return (float) Math.sqrt((x * x) + (y * y));
	}
		
	public static Vec2f add(Vec2f vec1, Vec2f vec2) {
		return new Vec2f(vec1.x + vec2.x, vec1.y + vec2.y);
	}
	
	public static Vec2f subtract(Vec2f vec1, Vec2f vec2) {
		return new Vec2f(vec1.x - vec2.x, vec1.y - vec2.y);
	}
	
	public static Vec2f scale(Vec2f vec, float scale) {
		return new Vec2f(vec.x * scale, vec.y * scale);
	}
	
	public static float dot(Vec2f vec1, Vec2f vec2) {
		return vec1.x * vec2.x + vec1.y * vec2.y;
	}
	
	public static float length(Vec2f vec) {
		return (float) Math.sqrt(vec.x * vec.x + vec.y * vec.y);
	}
	
	public static Vec2f normalize(Vec2f vec) {
		float length = (float) Math.sqrt(vec.x * vec.x + vec.y * vec.y);
		
		return new Vec2f(vec.x / length, vec.y / length);
	}
	
	public static Vec2f transform(Vec2f vec, Mat2f matrix) {
		float x = matrix.mat[0] * vec.x + matrix.mat[1] * vec.y;
		float y = matrix.mat[2] * vec.x + matrix.mat[3] * vec.y;
		
		return new Vec2f(x, y);
	}
	
	public static Vec2f affineTransfomr(Vec2f vec, Mat3f matrix) {
		float x = matrix.mat[0] * vec.x + matrix.mat[1] * vec.y + matrix.mat[2];
		float y = matrix.mat[3] * vec.x + matrix.mat[4] * vec.y + matrix.mat[5];
		
		return new Vec2f(x, y);
	}
}
