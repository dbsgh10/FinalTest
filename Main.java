package image;

import java.util.Random;


public class Main {
	// y = ax + b
	  
    // 1~30사이의 랜덤함수 = a
    public static int[] const_a() {
        Random r =  new Random();
        int[] arr = new int[4];

        for(int i=0;i<4;i++) {
            arr[i] = r.nextInt(30)+1;
        }
        return arr;
    }

    // 100~130사이의 랜덤함수 = b
    public static int[] const_b() {
        Random r =  new Random();
        int[] arr = new int[4];

        for(int i=0;i<4;i++) {
            arr[i] = r.nextInt(50)+80;
        }
        return arr;
    }
    
    //MSE 계산 함수
    public static int fx(int r, int s, int x, int y) {
        return (y - (r*x+s))* (y - (r*x+s));
    }
  
    // MSE 계산2
    public static int MSE(int a,int b,int x[], int y[]) {

        int msum = 0;

        for(int i=0;i<x.length;i++) {
            msum += fx(a, b,x[i],y[i]);            			
        }
        return msum;
    }

	static int Low_MSE=10000;
    static int t=0;
    static int u=0;
    public static int[] selection(int[] a,int[] b,int[] x,int[] y) {

        int sum = 0; 
        int totalsum= 0; 
	int[] match = new int[a.length];
        // MSE의 최소값을 구하는 코드가 들어갈 자리
        //for(int i=0;i<a.length;i++) {        	
        //    match[i] = MSE(a[i],b[i],x,y);            
        //}

       
        double [] ratio = new double[a.length];

        for(int i=0;i<a.length;i++) {
            if (i== 0) ratio[i] = (double)match[i]/(double) totalsum;
            else ratio[i] = ratio[i-1]+ (double)match[i] / (double)totalsum;
        }

        int[] choice = new int[a.length*2];
        Random r = new Random();

        for(int i=0;i<a.length;i++) {
            double p = r.nextDouble();

            if(p<ratio[0]) {
                choice[i] = a[0]; choice[i+a.length] = b[0];
            }
            else if(p<ratio[1]) {
                choice[i] = a[1]; choice[i+a.length] = b[1];
            }
            else if(p<ratio[2]) {
                choice[i] = a[2]; choice[i+a.length] = b[2];
            }
            else {
                choice[i] = a[3]; choice[i+a.length] = b[3];
            }
        }
        return choice;
    }


    public static String int8String(String x) {
        return String.format("%8s",x).replace(' ', '0');
    }

    public static String[] crossOver(int[] selection) {

        String[] inter = new String[selection.length];
   
        for(int i=0;i<selection.length/2;i+=2) {
            String bit1 = int8String(Integer.toBinaryString(selection[i]));
            String bit2 =  int8String(Integer.toBinaryString(selection[i+1]));

            inter[i] = bit1.substring(0,4) + bit2.substring(4,8);
            inter[i+1] = bit2.substring(0,4) + bit1.substring(4,8);
        }

   
        for(int i=selection.length/2 ;i<selection.length;i+=2) {
            String bit1 = int8String(Integer.toBinaryString(selection[i]));
            String bit2 =  int8String(Integer.toBinaryString(selection[i+1]));

            inter[i] = bit1.substring(0,4) + bit2.substring(4,8);
            inter[i+1] = bit2.substring(0,4) + bit1.substring(4,8);
        }

        return inter;
    }

    public static int invert(String inter) {

        Random r = new Random();
        int a = Integer.parseInt(inter,2);

        for(int i=0;i<inter.length();i++) {
            double p = (double) 1/ (double)35;
            if(r.nextDouble()<p) {
                a = 1 << i ^a;
            }
        }

        return a;
    }

    public static int[] mutation(String[] inter) {
        int[] arr = new int[inter.length];
        for(int i=0;i<inter.length;i++) {
            arr[i] = invert(inter[i]);
        }
        return arr;

    }


    public static void main(String[] args) {
    	
        int x[] = {1,2,7,16,24,29,30,33,40,45}; // 장교 근무 년수
        int y[] = {121,134,210,327,434,490,494,540,680,694}; // 근무 년수에 따른 기본급
	
        int a[] = const_a(); 
        int b[] = const_b(); 

          
        for(int i=0; i<100000; i++) {
        	
            int[] selection = selection(a,b,x,y);  // 선택연산
            String[] inter = crossOver(selection);  // 교차연산
            int[] mutation  = mutation(inter);  // 돌연변이 연산
            
            for(int j=0;j<a.length;j++) {
                a[j] = mutation[j];
                b[j] = mutation[j+4];
            }
        }

        System.out.print("함수 그래프 :" + t);
        System.out.println("x + " + u); 
    }
}
