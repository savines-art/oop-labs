package sys.pro;

/**
 * Concrete {@link AbstractTopSortTest} binding that runs {@link TopSort}
 * against the adjacency-list implementation {@link GraphList}.
 */
class TopSortListTest extends AbstractTopSortTest {

    /** {@inheritDoc} */
    @Override
    protected Graph createGraph() {
        return new GraphList();
    }
}