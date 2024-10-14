import java.util.;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        final String inputExp = ReadInput.read();

        QueueString operations;
        QueueString numbers;

        String[] numbersArr = inputExp.split([-+%]);
        String[] operArr = inputExp.split(d+);
        numbers = new LinkedList(Arrays.asList(numbersArr));
        operations = new LinkedList(Arrays.asList(operArr));

        Double res = Double.parseDouble(Objects.requireNonNull(numbers.poll()));

        while(!numbers.isEmpty()){
            String opr = operations.poll();

            Operate operate;
            switch(Objects.requireNonNull(opr)){
                case +
                    operate = new Add();
                    break;
                case -
                    operate = new Sub();
                    break;
                case 
                    operate = new Multiply();
                    break;
                case 
                    operate = new Divide();
                    break;
                case %
                    operate = new Modulus();
                    break;
                default
                    continue;
            }
            Double num = Double.parseDouble(Objects.requireNonNull(numbers.poll()));
            res = operate.getResult(res, num);
        }

        System.out.println(res);
    }
}
interface Operate {
    Double getResult(Double... numbers);
}

class ReadInput {
    public static String read(){
        Scanner scanner = new Scanner(System.in);

        System.out.println(Input Expression Example 432);
        String inputLine = scanner.nextLine();

        scanner.close();
        return inputLine;
    }
}
class Add implements Operate{
    @Override
    public Double getResult(Double... numbers){
        Double sum = 0.0;

        for(Double num numbers){
            sum += num;
        }
        return sum;
    }
}
class Divide implements Operate {
    @Override
    public Double getResult(Double... numbers){
        Double div = numbers[0];

        for(int i=1;i numbers.length;i++){
            div = numbers[i];
        }
        return div;
    }
}
class Modulus implements Operate{
    @Override
    public Double getResult(Double... numbers){
        Double mod = numbers[0];

        for (int i = 1; i  numbers.length; i++) {
            mod %= numbers[i];
        }
        return mod;
    }
}
class Multiply implements Operate {
    @Override
    public Double getResult(Double... numbers){
        Double mul = 1.0;

        for(Double num numbers){
            mul = num;
        }
        return mul;
    }

}

class Sub implements Operate{
    @Override
    public Double getResult(Double... numbers){
        Double sub = numbers[0];

        for(int i=1;i numbers.length;i++){
            sub -= numbers[i];
        }
        return sub;
    }
}