import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by Alina on 8.10.2016.
 */
public class BarrayTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    Barray barray;
    int barraySize;

    @Before
    public void setUp() throws Exception{
        barraySize = 15;
        barray = new Barray(barraySize);
        int[] arr = {9221, 4903, 723, 4755, 4535, 5065, 1543, 6735, 82, 5836, 2792};
        for (int i = 0; i < arr.length; i++) {
            barray.add(arr[i], i);
        }
    }

    @Test
    public void testAdd() throws Exception {
        Assert.assertTrue(barray.asString().equals("9221, 4903, 723, 4755\n"
                + "4535, 5065, 1543, 6735\n"
                + "82, 5836, 2792\n"));
    }

    @Test
    public void testSearch() throws Exception {
        Assert.assertEquals(barray.search(222), -1);
    }

    @Test
    public void testSearch2() throws Exception {
        Assert.assertEquals(barray.search(2792), 10);
    }

    @Test
    public void testSearch3() throws Exception {
        Assert.assertEquals(barray.search(723), 2);
    }

    @Test
    public void testGet() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Index is out of range");
        barray.get(11);
    }

    @Test
    public void testGet2() throws Exception {
        Assert.assertEquals(barray.get(5), 5065);
    }

    @Test
    public void testSet() throws Exception {
        thrown.expect(Exception.class);
        thrown.expectMessage("Index is out of range");
        barray.set(100, 12);
    }

    @Test
    public void testSet2() throws Exception {
        barray.set(42, 6);
        Assert.assertEquals(barray.get(6), 42);
    }
}
