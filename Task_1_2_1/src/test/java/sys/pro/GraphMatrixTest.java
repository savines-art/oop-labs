package sys.pro;

class GraphMatrixTest extends AbstractGraphTest {

    @Override
    protected Graph createGraph() {
        return new GraphMatrix();
    }
}