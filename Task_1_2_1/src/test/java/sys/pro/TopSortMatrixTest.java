package sys.pro;

/**
 * Concrete {@link AbstractTopSortTest} binding that runs {@link TopSort}
 * against the adjacency-matrix implementation {@link GraphMatrix}.
 */
class TopSortMatrixTest extends AbstractTopSortTest {

    /** {@inheritDoc} */
    @Override
    protected Graph createGraph() {
        return new GraphMatrix();
    }
}