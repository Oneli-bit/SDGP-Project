interface Calculations {

    public void operation(int num);

}



class CalculatorA implements Calculations{

    int out;



    public void operation(int num){

        out = num / 2;

    }

}



class testInterface{



    public static void main(String[] args){

        int i, sum;

        sum = 8;

        try {

            for(i = -1; i<3; i++){

                sum = sum / i;

            }

        }

        catch (ArithmeticException exc){

            System.out.print("0 ");

        }

        System.out.println(sum);



    }

}