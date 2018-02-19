package com.lifei.k3cloudwebapi;

import java.util.ArrayList;
import java.util.List;

public class K3CloudApiClient extends ApiClient {

    public K3CloudApiClient(String serverUrl) {
        super(serverUrl);
    }

    public String excuteOperation(String formid, String opNumber, String data)
            throws Exception {
        return (String) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExcuteOperation", new Object[]{formid, opNumber, data}, String.class);
    }

    public String save(String formid, String data)
            throws Exception {
        return (String) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Save", new Object[]{formid, data}, String.class);
    }

    public String batchSave(String formid, String data)
            throws Exception {
        return (String) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.BatchSave", new Object[]{formid, data}, String.class);
    }

    public String audit(String formid, String data)
            throws Exception {
        return (String) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Audit", new Object[]{formid, data}, String.class);
    }

    public String delete(String formid, String data)
            throws Exception {
        return (String) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Delete", new Object[]{formid, data}, String.class);
    }

    public String unAudit(String formid, String data)
            throws Exception {
        return (String) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.UnAudit", new Object[]{formid, data}, String.class);
    }

    public String submit(String formid, String data)
            throws Exception {
        return (String) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Submit", new Object[]{formid, data}, String.class);
    }

    public String view(String formid, String data)
            throws Exception {
        return (String) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.View", new Object[]{formid, data}, String.class);
    }

    public List<List<Object>> executeBillQuery(String data)
            throws Exception {
        return (List) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.ExecuteBillQuery", new Object[]{data}, new ArrayList().getClass());
    }

    public String draft(String formid, String data)
            throws Exception {
        return (String) execute("Kingdee.BOS.WebApi.ServicesStub.DynamicFormService.Draft", new Object[]{formid, data}, String.class);
    }
}
