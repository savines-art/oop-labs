package sys.pro;

class TopSortIncTest extends AbstractTopSortTest {

    @Override
    protected Graph createGraph() {
        return new GraphInc();
    }
}