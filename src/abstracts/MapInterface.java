package abstracts;

public interface MapInterface {

    default void print() {
        System.out.println(status());

    }

    default String status() {
        String s = "";
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 100; j++)
                s += "_";
            s += "\n";
        }
        return s;

    }

}
