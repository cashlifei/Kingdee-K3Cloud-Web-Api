# Kingdee K3Cloud Web Api
 使用示例：

/**
 *
 * @author Carl Li <cashlifei@hotmail.com>
 */
import java.util.List;
import com.lifei.k3cloudwebapi.ApiClient;
import com.lifei.k3cloudwebapi.K3CloudApiClient;

public class DemoTest {

    static String K3CloudURL = "<a href=\"http://localhost:1600/\">http://localhost:1600/</a>";
    static String dbId = "5805e44292bf19";
    static String uid = "administrator";
    static String pwd = "888888";
    static int lang = 2052;

    public static void main(String[] args) throws Exception {
        //saveCurrency();
        //query;
        //batchsaveCurrency();
        testall();
    }

    public static void testall() throws Exception {
        K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
        Boolean result = client.login(dbId, uid, pwd, lang);
        if (result) {
            String sContent = "{\"FormId\":\"PUR_PurchaseOrder\","
                    +// 采购订单formid
                    "\"TopRowCount\":2,"
                    +// 最多允许查询的数量，0或者不要此属性表示不限制
                    "\"Limit\":3,"
                    +// 分页取数每页允许获取的数据，最大不能超过2000
                    "\"StartRow\":0,"
                    +// 分页取数开始行索引，从0开始，例如每页10行数据，第2页开始是10，第3页开始是20
                    //""FilterString":"FMaterialId.FNumber like 'HG_TEST%'","+// 过滤条件
                    //""FilterString":"FBillNo='CGDD000008'","+// 过滤条件
                    "\"OrderString\":\"FID ASC\","
                    +// 排序条件
                    "\"FieldKeys\"\"FID,FSupplierId,FMaterialId.FNumber,FMaterialName\"}";// 获取采购订单数据参数，内码，供应商id，物料id,物料编码，物料名称

            List<List<Object>> sResult = client.executeBillQuery(sContent);//("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save", new Object[]{sFormId,sContent},String.class);

            System.out.println("CurrencyTest success:" + sResult);
            String sFormId = "BD_Currency";
            String content = "{\"Numbers\":[\"PRENB00016\"]}";
            String result1 = client.delete(sFormId, content);

            System.out.println("CurrencyTest success:" + result1);
        }
    }

    public static void saveCurrency() throws Exception {
        String K3CloudURL = "<A href=\"http://localhost:1600/\">http://localhost:1600/</A>";
        String dbId = "5805e44292bf19";
        String uid = "administrator";
        int lang = 2052;

        ApiClient client = new ApiClient(K3CloudURL);

        Boolean result = client.login(dbId, uid, pwd, lang);
        if (result) {
            String sFormId = "BD_Currency";
            String sContent = "{\"Creator\":\"\",\"NeedUpDateFields\":[\"\"],\"Model\":{\"FNumber\":\"PRENB00016\",\"FName\":\"你是噢吗6？\",\"FCODE\":\"CNYY\",\"FPRICEDIGITS\":6,\"FAMOUNTDIGITS\":2,\"FPRIORITY\":0,\"FCURRENCYSYMBOLID\":{\"FNumber\":\"CS002\"},\"FIsShowCSymbol\":true}}";

            String sResult = client.execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save", new Object[]{sFormId, sContent}, String.class);

            System.out.println("CurrencyTest success:" + sResult);
        }
    }

    public static void batchsaveCurrency() throws Exception {
        K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
        Boolean result = client.login(dbId, uid, pwd, lang);
        if (result) {
            String sFormId = "BD_Currency";
            String sContent = "{\"Creator\":\"\",\"NeedUpDateFields\":[\"\"],\"Model\":[{\"FNumber\":\"PRENB000216\",\"FName\":\"你是噢吗26？\",\"FCODE\":\"CNYY\",\"FPRICEDIGITS\":6,\"FAMOUNTDIGITS\":2,\"FPRIORITY\":0,\"FCURRENCYSYMBOLID\":{\"FNumber\":\"CS002\"},\"FIsShowCSymbol\":true},{\"FNumber\":\"PRENB00036\",\"FName\":\"你是噢吗16？\",\"FCODE\":\"CNYY\",\"FPRICEDIGITS\":6,\"FAMOUNTDIGITS\":2,\"FPRIORITY\":0,\"FCURRENCYSYMBOLID\":{\"FNumber\":\"CS002\"},\"FIsShowCSymbol\":true}]}";

            String sResult = client.batchSave(sFormId, sContent);

            System.out.println("CurrencyTest success:" + sResult);
        }
    }

    public static void query() throws Exception {
        K3CloudApiClient client = new K3CloudApiClient(K3CloudURL);
        Boolean result = client.login(dbId, uid, pwd, lang);
        if (result) {
            String sContent = "{\"FormId\":\"PUR_PurchaseOrder\","
                    +// 采购订单formid
                    "\"TopRowCount\":2,"
                    +// 最多允许查询的数量，0或者不要此属性表示不限制
                    "\"Limit\":3,"
                    +// 分页取数每页允许获取的数据，最大不能超过2000
                    "\"StartRow\":0,"
                    +// 分页取数开始行索引，从0开始，例如每页10行数据，第2页开始是10，第3页开始是20
                    //""FilterString":"FMaterialId.FNumber like 'HG_TEST%'","+// 过滤条件
                    //""FilterString":"FBillNo='CGDD000008'","+// 过滤条件
                    "\"OrderString\":\"FID ASC\","
                    +// 排序条件
                    "\"FieldKeys\":\"FID,FSupplierId,FMaterialId.FNumber,FMaterialName\"}";// 获取采购订单数据参数，内码，供应商id，物料id,物料编码，物料名称

            List<List<Object>> sResult = client.executeBillQuery(sContent);//("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save", new Object[]{sFormId,sContent},String.class);

            System.out.println("CurrencyTest success:" + sResult);
        }
    }
}
