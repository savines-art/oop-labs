package sys.pro;

class TopSortListTest extends AbstractTopSortTest {

    @Override
    protected Graph createGraph() {
        return new GraphList();
    }
}