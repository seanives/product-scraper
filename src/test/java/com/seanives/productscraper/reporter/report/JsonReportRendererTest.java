package com.seanives.productscraper.reporter.report;

import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonReportRendererTest {

  public class A {
    private List<AA> a;
    private BB b;

    public List<AA> getA() {
      return a;
    }

    public void setA(List<AA> a) {
      this.a = a;
    }

    public BB getB() {
      return b;
    }

    public void setB(BB b) {
      this.b = b;
    }
  }

  public class AA {
    private String aa;

    public String getAa() {
      return aa;
    }

    public void setAa(String aa) {
      this.aa = aa;
    }
  }

  public class BB {
    private String bb;

    public String getBb() {
      return bb;
    }

    public void setBb(String bb) {
      this.bb = bb;
    }
  }

  A testModelToRender =
      new A() {
        {
          setA(
              Arrays.asList(
                  new AA() {
                    {
                      setAa("aa");
                    }
                  }));
          setB(
              new BB() {
                {
                  setBb("bb");
                }
              });
        }
      };

  Map<String, Object> testRenderedReport =
      new HashMap<String, Object>() {
        {
          put(
              "a",
              Arrays.asList(
                  new HashMap<String, Object>() {
                    {
                      put("aa", "aa");
                    }
                  }));
          put(
              "b",
              new HashMap<String, Object>() {
                {
                  put("bb", "bb");
                }
              });
        }
      };

  final JsonReportRenderer renderer = new JsonReportRenderer();

  public JsonReportRendererTest() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  @DisplayName("should return a rendered report when given a model")
  void getRendered() {
    Map<String, Object> renderedReport = renderer.getRendered(testModelToRender);
    assertThat(renderedReport, is(notNullValue()));
    assertThat(renderedReport, is(equalTo(testRenderedReport)));
  }

  @Test
  @DisplayName("should transform a given rendered report into a json string")
  void getJsonString() {
    Map<String, Object> renderedReport = renderer.getRendered(testModelToRender);
    String json = renderer.getJsonString(renderedReport);
    assertThat(json, is(notNullValue()));
    assertThat(json.contains("\"a\":[{\"aa\":\"aa\"}]"), is(true));
    assertThat(json.contains("\"b\":{\"bb\":\"bb\"}"), is(true));
  }
}
