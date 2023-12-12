package com.sca.in_telligent.ui.main;

import com.sca.in_telligent.openapi.data.network.model.Building;
import java.util.function.Function;

/* compiled from: D8$$SyntheticClass */
/* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
public final /* synthetic */ class MainActivity$$ExternalSyntheticLambda11 implements Function {
    public static final /* synthetic */ MainActivity$$ExternalSyntheticLambda11 INSTANCE = new MainActivity$$ExternalSyntheticLambda11();

    private /* synthetic */ MainActivity$$ExternalSyntheticLambda11() {
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String name;
        name = ((Building) obj).getName();
        return name;
    }
}
