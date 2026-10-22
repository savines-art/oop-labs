package sys.pro;

/**
 * Concrete {@link AbstractTopSortTest} binding that runs {@link TopSort}
 * against the incidence-matrix implementation {@link GraphInc}.
 */
class TopSortIncTest extends AbstractTopSortTest {

    /** {@inheritDoc} */
    @Override
    protected Graph createGraph() {
        return new GraphInc();
    }
}