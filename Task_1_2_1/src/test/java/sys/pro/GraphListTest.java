package sys.pro;

class GraphListTest extends AbstractGraphTest {

    @Override
    protected Graph createGraph() {
        return new GraphList();
    }
}