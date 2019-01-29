package online.gettrained.backend.repositories;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;

public class UtilsTest {

  @Test
  public void getListBlobIdFromCourseContentLocalInJsonFormat() {
    String json = "{\"width\":950,\"height\":500,\"grid\":true,\"previewMode\":false,\"elements\":[{\"type\":\"VIDEO\",\"width\":457,\"height\":257,\"top\":22,\"left\":42.111111111111086,\"loading\":false,\"resize\":false,\"zIndex\":1,\"id\":0,\"name\":\"11.mp4\",\"size\":4477178,\"value\":{\"url\":\"fe/contents/fm/file/-1/0/43\",\"fullFileUrl\":\"fe/contents/fm/fullFile/-1/0/43\"},\"maxWidth\":1600,\"maxHeight\":900,\"duration\":79.5},{\"type\":\"IMAGE\",\"width\":334,\"height\":100,\"top\":322,\"left\":70,\"loading\":false,\"resize\":false,\"zIndex\":1,\"id\":1,\"name\":\"trolltunga.jpg\",\"size\":45941,\"value\":{\"url\":\"fe/contents/fm/file/-1/0/44\",\"fullFileUrl\":\"fe/contents/fm/fullFile/-1/0/44\"},\"maxWidth\":1000,\"maxHeight\":300},{\"type\":\"AUDIO\",\"width\":302,\"height\":34,\"top\":120,\"left\":614,\"loading\":false,\"resize\":false,\"zIndex\":1,\"id\":2,\"name\":\"Отказ 1 (2).mp3\",\"size\":1538647,\"value\":{\"url\":\"fe/contents/fm/file/-1/0/45\",\"fullFileUrl\":\"fe/contents/fm/fullFile/-1/0/45\"}}],\"newId\":3,\"editableElementId\":-1,\"disableEditableAll\":false,\"deleteElementModal\":false,\"deleteElementId\":-1,\"settingsElementModal\":false}";
    Set<Long> list = Utils.getListBlobIdFromCourseContentLocalInJsonFormat(json);
    Assert.assertArrayEquals(list.toArray(), Arrays.asList(43L, 44L, 45L).toArray());
  }

  @Test
  public void getBlobIdFromUrl() {
    Long blobId = null;
    String url = "fe/contents/fm/file/-1/0/43";
    Matcher matcher = Pattern.compile("(\\d+)$").matcher(url);
    if (matcher.find()) {
      blobId = Long.parseLong(matcher.group(1));
    }
    Assert.assertEquals(blobId.longValue(), 43);
  }

  @Test
  public void getCompanyIdFromUrl() {
    Long companyId = null;
    String url = "fe/contents/fm/file/-1/0/43";
    Matcher matcher = Pattern.compile("/(-?\\d+)").matcher(url);
    if (matcher.find()) {
      companyId = Long.parseLong(matcher.group(1));
    }
    Assert.assertEquals(companyId.longValue(), -1);
  }

  @Test
  public void replaceBlobIdInCourseContentLocalInJsonFormat() {
    String json1 = "{\"width\":950,\"height\":500,\"grid\":true,\"previewMode\":false,\"elements\":[{\"type\":\"TEXT\",\"width\":212,\"height\":99,\"top\":37,\"left\":39,\"zIndex\":1,\"id\":0,\"value\":\"<p>Topic 1 Lesson 1.</p>\"},{\"type\":\"AUDIO\",\"width\":302,\"height\":34,\"top\":63,\"left\":365,\"loading\":false,\"resize\":false,\"zIndex\":1,\"id\":1,\"name\":\"Отказ 1 (1).mp3\",\"size\":1538647,\"value\":{\"url\":\"fe/contents/fm/file/-1/0/2\",\"fullFileUrl\":\"fe/contents/fm/fullFile/-1/0/2\"}}],\"newId\":2,\"editableElementId\":-1,\"disableEditableAll\":false,\"deleteElementModal\":false,\"deleteElementId\":-1,\"settingsElementModal\":false}";
    String json2 = "{\"width\":950,\"height\":500,\"grid\":true,\"previewMode\":false,\"elements\":[{\"type\":\"TEXT\",\"width\":300,\"height\":100,\"top\":30,\"left\":235,\"zIndex\":1,\"id\":0,\"value\":\"<p>Topic 1 Lesson 2</p>\"},{\"type\":\"VIDEO\",\"width\":396,\"height\":223,\"top\":187,\"left\":155.1111111111111,\"loading\":false,\"resize\":false,\"zIndex\":1,\"id\":1,\"name\":\"17 - Environment Setup - Set up Webpack.mp4\",\"size\":33100601,\"value\":{\"url\":\"fe/contents/fm/file/-1/0/3\",\"fullFileUrl\":\"fe/contents/fm/fullFile/-1/0/3\"},\"maxWidth\":1280,\"maxHeight\":720,\"duration\":470.924}],\"newId\":2,\"editableElementId\":-1,\"disableEditableAll\":false,\"deleteElementModal\":false,\"deleteElementId\":-1,\"settingsElementModal\":false}";
    String json3 = "{\"width\":950,\"height\":500,\"grid\":true,\"previewMode\":false,\"elements\":[{\"type\":\"TEXT\",\"width\":300,\"height\":100,\"top\":71,\"left\":149,\"zIndex\":1,\"id\":0,\"value\":\"<p>WTopic 2 Lesson 1</p>\"},{\"type\":\"PDF\",\"width\":300,\"height\":102,\"top\":50,\"left\":527,\"loading\":false,\"resize\":false,\"zIndex\":1,\"id\":1,\"name\":\"TA_PdfFileTitle_20171215_211129.pdf\",\"size\":49907,\"value\":{\"url\":\"fe/contents/fm/file/-1/0/4\",\"fullFileUrl\":\"fe/contents/fm/fullFile/-1/0/4\"}}],\"newId\":2,\"editableElementId\":-1,\"disableEditableAll\":false,\"deleteElementModal\":false,\"deleteElementId\":-1,\"settingsElementModal\":false}";
    String json4 = "{\"width\":950,\"height\":500,\"grid\":true,\"previewMode\":false,\"elements\":[{\"type\":\"TEXT\",\"width\":300,\"height\":100,\"top\":51,\"left\":44,\"zIndex\":1,\"id\":0,\"value\":\"<p>Topic 2 Lesson 2.</p>\"},{\"type\":\"IMAGE\",\"width\":441,\"height\":174,\"top\":66,\"left\":407,\"loading\":false,\"resize\":false,\"zIndex\":1,\"id\":1,\"name\":\"Знімок екрана 2017-10-30 о 16.50.57.png\",\"size\":131587,\"value\":{\"url\":\"fe/contents/fm/file/-1/0/5\",\"fullFileUrl\":\"fe/contents/fm/fullFile/-1/0/5\"},\"maxWidth\":1544,\"maxHeight\":610}],\"newId\":2,\"editableElementId\":-1,\"disableEditableAll\":false,\"deleteElementModal\":false,\"deleteElementId\":-1,\"settingsElementModal\":false}";
    Map<Long, Long> map1 = new HashMap();
    map1.put(2L, 12L);
    Map<Long, Long> map2 = new HashMap();
    map2.put(3L, 13L);
    Map<Long, Long> map3 = new HashMap();
    map2.put(4L, 14L);
    Map<Long, Long> map4 = new HashMap();
    map2.put(5L, 15L);
    Optional<List<Map<Long, Long>>> bindBlobIdList = Optional.of(Arrays.asList(map1, map2, map3, map4));
    String newJson1 = Utils.replaceCompanyIdAndBlobIdInCourseContentLocalInJsonFormat(json1, 11L, bindBlobIdList);
    Assert.assertTrue(newJson1.contains("fe/contents/fm/file/11/0/12"));
    Assert.assertTrue(newJson1.contains("fe/contents/fm/fullFile/11/0/12"));
    String newJson2 = Utils.replaceCompanyIdAndBlobIdInCourseContentLocalInJsonFormat(json2, 11L, bindBlobIdList);
    Assert.assertTrue(newJson2.contains("fe/contents/fm/file/11/0/13"));
    Assert.assertTrue(newJson2.contains("fe/contents/fm/fullFile/11/0/13"));
    String newJson3 = Utils.replaceCompanyIdAndBlobIdInCourseContentLocalInJsonFormat(json3, 11L, bindBlobIdList);
    Assert.assertTrue(newJson3.contains("fe/contents/fm/file/11/0/14"));
    Assert.assertTrue(newJson3.contains("fe/contents/fm/fullFile/11/0/14"));
    String newJson4 = Utils.replaceCompanyIdAndBlobIdInCourseContentLocalInJsonFormat(json4, 11L, bindBlobIdList);
    Assert.assertTrue(newJson4.contains("fe/contents/fm/file/11/0/15"));
    Assert.assertTrue(newJson4.contains("fe/contents/fm/fullFile/11/0/15"));
  }
}
