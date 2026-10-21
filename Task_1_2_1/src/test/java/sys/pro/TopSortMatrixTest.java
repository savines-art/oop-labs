package sys.pro;

class TopSortMatrixTest extends AbstractTopSortTest {

    @Override
    protected Graph createGraph() {
        return new GraphMatrix();
    }
}