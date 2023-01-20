package Algorithm;

public class Bounds
{
    Solution solution;
    int value;

    public Bounds(Solution solution, int value)
    {
        this.solution = solution;
        this.value = value;
    }

    public Solution getSolution() {
        return solution;
    }

    public int getValue() {
        return value;
    }
}
