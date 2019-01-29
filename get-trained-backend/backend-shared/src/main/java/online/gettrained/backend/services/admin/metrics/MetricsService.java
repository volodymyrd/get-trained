package online.gettrained.backend.services.admin.metrics;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
//    private final List<PublicMetrics> publicMetrics;
//
//
//    @Autowired
//    public MetricsService(Collection<PublicMetrics> publicMetrics) {
//        Assert.notNull(publicMetrics, "PublicMetrics must not be null");
//        this.publicMetrics = new ArrayList<PublicMetrics>(publicMetrics);
//        AnnotationAwareOrderComparator.sort(this.publicMetrics);
//    }

  public Map<String, Object> invoke() {
    Map<String, Object> result = new LinkedHashMap<String, Object>();
//        List<PublicMetrics> metrics = new ArrayList<PublicMetrics>(this.publicMetrics);
//        for (PublicMetrics publicMetric : metrics) {
//            try {
//                for (Metric<?> metric : publicMetric.metrics()) {
//                    result.put(metric.getName(), metric.getValue());
//                }
//            }
//            catch (Exception ex) {
//                // Could not evaluate metrics
//            }
//        }
    return result;
  }
}
