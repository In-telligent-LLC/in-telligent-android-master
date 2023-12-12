package com.sca.in_telligent.openapi.data.network.error;

import java.util.ArrayList;

/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public class ErrorResponse {
    public ArrayList<ErrorObject> errorObjects;

    public ArrayList<ErrorObject> getErrorObjects() {
        return this.errorObjects;
    }

    public void setErrorObjects(ArrayList<ErrorObject> arrayList) {
        this.errorObjects = arrayList;
    }

    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
    public class ErrorObject {
        public String code;
        public String detail;
        public Meta meta;
        public Source source;
        public String statusCode;
        public String title;

        public ErrorObject() {
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String str) {
            this.code = str;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String getDetail() {
            return this.detail;
        }

        public void setDetail(String str) {
            this.detail = str;
        }

        public Source getSource() {
            return this.source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public String getStatusCode() {
            return this.statusCode;
        }

        public Meta getMeta() {
            return this.meta;
        }

        /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
        public class Source {
            public String pointer;

            public Source() {
            }
        }

        /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
        public class Meta {
            public String userId;

            public Meta() {
            }

            public String getUserId() {
                return this.userId;
            }
        }
    }
}
