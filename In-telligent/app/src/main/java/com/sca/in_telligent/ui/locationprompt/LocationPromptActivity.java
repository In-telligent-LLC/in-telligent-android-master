//package com.sca.in_telligent.ui.locationprompt;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import androidx.activity.ComponentActivity;
//import androidx.activity.compose.ComponentActivityKt;
//import androidx.appcompat.app.AlertDialog;
//import androidx.compose.foundation.BackgroundKt;
//import androidx.compose.foundation.BorderStroke;
//import androidx.compose.foundation.ScrollKt;
//import androidx.compose.foundation.gestures.FlingBehavior;
//import androidx.compose.foundation.interaction.MutableInteractionSource;
//import androidx.compose.foundation.layout.Arrangement;
//import androidx.compose.foundation.layout.ColumnKt;
//import androidx.compose.foundation.layout.ColumnScope;
//import androidx.compose.foundation.layout.ColumnScopeInstance;
//import androidx.compose.foundation.layout.PaddingKt;
//import androidx.compose.foundation.layout.PaddingValues;
//import androidx.compose.foundation.layout.RowScope;
//import androidx.compose.foundation.layout.SizeKt;
//import androidx.compose.foundation.layout.SpacerKt;
//import androidx.compose.material.ButtonColors;
//import androidx.compose.material.ButtonElevation;
//import androidx.compose.material.ButtonKt;
//import androidx.compose.material.IconKt;
//import androidx.compose.material.MaterialTheme;
//import androidx.compose.runtime.Applier;
//import androidx.compose.runtime.ComposablesKt;
//import androidx.compose.runtime.Composer;
//import androidx.compose.runtime.ComposerKt;
//import androidx.compose.runtime.CompositionContext;
//import androidx.compose.runtime.ScopeUpdateScope;
//import androidx.compose.runtime.SkippableUpdater;
//import androidx.compose.runtime.Updater;
//import androidx.compose.runtime.internal.ComposableLambdaKt;
//import androidx.compose.ui.Alignment;
//import androidx.compose.ui.Modifier;
//import androidx.compose.ui.graphics.Shadow;
//import androidx.compose.ui.graphics.Shape;
//import androidx.compose.ui.graphics.painter.Painter;
//import androidx.compose.ui.layout.LayoutKt;
//import androidx.compose.ui.layout.MeasurePolicy;
//import androidx.compose.ui.node.ComposeUiNode;
//import androidx.compose.ui.platform.CompositionLocalsKt;
//import androidx.compose.ui.res.ColorResources_androidKt;
//import androidx.compose.ui.res.PainterResources_androidKt;
//import androidx.compose.ui.res.StringResources_androidKt;
//import androidx.compose.ui.text.TextStyle;
//import androidx.compose.ui.text.font.FontFamily;
//import androidx.compose.ui.text.font.FontStyle;
//import androidx.compose.ui.text.font.FontSynthesis;
//import androidx.compose.ui.text.font.FontWeight;
//import androidx.compose.ui.text.intl.LocaleList;
//import androidx.compose.ui.text.style.BaselineShift;
//import androidx.compose.ui.text.style.TextAlign;
//import androidx.compose.ui.text.style.TextDecoration;
//import androidx.compose.ui.text.style.TextDirection;
//import androidx.compose.ui.text.style.TextGeometricTransform;
//import androidx.compose.ui.text.style.TextIndent;
//import androidx.compose.ui.unit.Density;
//import androidx.compose.ui.unit.Dp;
//import androidx.compose.ui.unit.LayoutDirection;
//import androidx.compose.ui.unit.TextUnitKt;
//import com.facebook.appevents.internal.ViewHierarchyConstants;
//import com.facebook.internal.NativeProtocol;
//import com.sca.in_telligent.R;
//import com.sca.in_telligent.ui.base.BaseActivity;
//import com.sca.in_telligent.util.LocationUtil;
//
//import kotlin.Metadata;
//import kotlin.Unit;
//import kotlin.jvm.functions.Function0;
//import kotlin.jvm.functions.Function2;
//import kotlin.jvm.functions.Function3;
//import kotlin.jvm.internal.DefaultConstructorMarker;
//import kotlin.jvm.internal.Intrinsics;
//
//@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0005\b\u0007\u0018\u0000 -2\u00020\u0001:\u0002-.B\u0005¢\u0006\u0002\u0010\u0002JW\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u0015\u0010\u0016J1\u0010\u0017\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000eH\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001b\u0010\u001cJ\r\u0010\u001d\u001a\u00020\u0006H\u0007¢\u0006\u0002\u0010\u001eJW\u0010\u001f\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0007ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b \u0010\u0016J\b\u0010!\u001a\u00020\u0006H\u0002J\u0012\u0010\"\u001a\u00020\u00062\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J+\u0010%\u001a\u00020\u00062\u0006\u0010&\u001a\u00020\u00142\f\u0010'\u001a\b\u0012\u0004\u0012\u00020\n0(2\u0006\u0010)\u001a\u00020*H\u0016¢\u0006\u0002\u0010+J\b\u0010,\u001a\u00020\u0006H\u0014R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006/"}, d2 = {"Lcom/sca/in_telligent/ui/locationprompt/LocationPromptActivity;", "Lcom/sca/in_telligent/ui/base/BaseActivity;", "()V", "callback", "Lcom/sca/in_telligent/ui/locationprompt/LocationPromptActivity$LocationPromptCallBack;", "BoldText", "", "modifier", "Landroidx/compose/ui/Modifier;", ViewHierarchyConstants.TEXT_KEY, "", "textAlign", "Landroidx/compose/ui/text/style/TextAlign;", "color", "Landroidx/compose/ui/graphics/Color;", "fontSize", "Landroidx/compose/ui/unit/TextUnit;", "textStyle", "Landroidx/compose/ui/text/TextStyle;", "maxLines", "", "BoldText-1DmQkMM", "(Landroidx/compose/ui/Modifier;Ljava/lang/String;IJJLandroidx/compose/ui/text/TextStyle;ILandroidx/compose/runtime/Composer;II)V", "BuzzBellIcon", "painter", "Landroidx/compose/ui/graphics/painter/Painter;", "tint", "BuzzBellIcon-FNF3uiM", "(Landroidx/compose/ui/Modifier;Landroidx/compose/ui/graphics/painter/Painter;JLandroidx/compose/runtime/Composer;II)V", "LocationPromptComposable", "(Landroidx/compose/runtime/Composer;I)V", "SemiBoldText", "SemiBoldText-1DmQkMM", "nextBtnTapped", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "requestCode", NativeProtocol.RESULT_ARGS_PERMISSIONS, "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "setUp", "Companion", "LocationPromptCallBack", "app_productionRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
//public final class LocationPromptActivity extends BaseActivity {
//    private static final String TAG;
//    private LocationPromptCallBack callback;
//    public static final Companion Companion = new Companion(null);
//    public static final int $stable = 8;
//
//    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&¨\u0006\u0004"}, d2 = {"Lcom/sca/in_telligent/ui/locationprompt/LocationPromptActivity$LocationPromptCallBack;", "", "onLocationNext", "", "app_productionRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
//
//
//
//    @Override
//    protected void setUp() {
//    }
//
//    @Override
//    public void onCreate(Bundle bundle) {
//        super.onCreate(bundle);
//        getActivityComponent().inject(this);
//        ComponentActivityKt.setContent$default((ComponentActivity) this, (CompositionContext) null, ComposableLambdaKt.composableLambdaInstance(-985532872, true, new Function2<Composer, Integer, Unit>() { // from class: com.sca.in_telligent.ui.locationprompt.LocationPromptActivity$onCreate$1
//            {
//                super();
//            }
//
//            @Override // kotlin.jvm.functions.Function2
//            public /* bridge */ /* synthetic */ Unit invoke(Composer composer, Integer num) {
//                invoke(composer, num.intValue());
//                return Unit.INSTANCE;
//            }
//
//            public final void invoke(Composer composer, int i) {
//                if (((i & 11) ^ 2) == 0 && composer.getSkipping()) {
//                    composer.skipToGroupEnd();
//                } else {
//                    LocationPromptActivity.this.LocationPromptComposable(composer, 8);
//                }
//            }
//        }), 1, (Object) null);
//    }
//
//    public final void m256BuzzBellIconFNF3uiM(Modifier modifier, final Painter painter, long j, Composer composer, final int i, final int i2) {
//        long j2;
//        int i3;
//        Intrinsics.checkNotNullParameter(painter, "painter");
//        Composer startRestartGroup = composer.startRestartGroup(653003881);
//        ComposerKt.sourceInformation(startRestartGroup, "C(BuzzBellIcon)P(!,2:c#ui.graphics.Color)");
//        Modifier modifier2 = (i2 & 1) != 0 ? (Modifier) Modifier.Companion : modifier;
//        if ((i2 & 4) != 0) {
//            i3 = i & (-897);
//            j2 = MaterialTheme.INSTANCE.getColors(startRestartGroup, 8).getPrimaryVariant-0d7_KjU();
//        } else {
//            j2 = j;
//            i3 = i;
//        }
//        new IconKt(painter, StringResources_androidKt.stringResource((int) R.string.disconnect, startRestartGroup, 0), modifier2, j2, startRestartGroup, 8 | ((i3 << 6) & 896) | ((i3 << 3) & 7168), 0);
//        ScopeUpdateScope endRestartGroup = startRestartGroup.endRestartGroup();
//        if (endRestartGroup == null) {
//            return;
//        }
//        final Modifier modifier3 = modifier2;
//        final long j3 = j2;
//        endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() { // from class: com.sca.in_telligent.ui.locationprompt.LocationPromptActivity$BuzzBellIcon$1
//            /* JADX INFO: Access modifiers changed from: package-private */
//            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
//            {
//                super(2);
//            }
//
//            @Override // kotlin.jvm.functions.Function2
//            public /* bridge */ /* synthetic */ Unit invoke(Composer composer2, Integer num) {
//                invoke(composer2, num.intValue());
//                return Unit.INSTANCE;
//            }
//
//            public final void invoke(Composer composer2, int i4) {
//                LocationPromptActivity.this.m256BuzzBellIconFNF3uiM(modifier3, painter, j3, composer2, i | 1, i2);
//            }
//        });
//    }
//
//    /* JADX INFO: Access modifiers changed from: private */
//    public final void nextBtnTapped() {
//        if (LocationUtils.isPermissionsGranted((Context) this)) {
//            finish();
//        } else {
//            LocationUtils.sendLocationPermissionRequest((Activity) this);
//        }
//    }
//
//    public final void LocationPromptComposable(Composer composer, final int i) {
//        Composer startRestartGroup = composer.startRestartGroup(573928370);
//        ComposerKt.sourceInformation(startRestartGroup, "C(LocationPromptComposable)");
//        Modifier verticalScroll$default = ScrollKt.verticalScroll$default(PaddingKt.padding-VpY3zN4$default(BackgroundKt.background-bw27NRU$default(SizeKt.fillMaxSize$default(Modifier.Companion, 0.0f, 1, (Object) null), ColorResources_androidKt.colorResource((int) R.color.blue, startRestartGroup, 0), (Shape) null, 2, (Object) null), Dp.constructor-impl(30), 0.0f, 2, (Object) null), ScrollKt.rememberScrollState(0, startRestartGroup, 0, 1), false, (FlingBehavior) null, false, 14, (Object) null);
//        Alignment.Horizontal centerHorizontally = Alignment.Companion.getCenterHorizontally();
//        startRestartGroup.startReplaceableGroup(-1113031299);
//        ComposerKt.sourceInformation(startRestartGroup, "C(Column)P(2,3,1)71@3450L61,72@3516L133:Column.kt#2w3rfo");
//        MeasurePolicy columnMeasurePolicy = ColumnKt.columnMeasurePolicy(Arrangement.INSTANCE.getCenter(), centerHorizontally, startRestartGroup, 0);
//        startRestartGroup.startReplaceableGroup(1376089335);
//        ComposerKt.sourceInformation(startRestartGroup, "C(Layout)P(!1,2)71@2788L7,72@2843L7,73@2855L389:Layout.kt#80mrfh");
//        ComposerKt.sourceInformationMarkerStart(startRestartGroup, 103361330, "C:CompositionLocal.kt#9igjgp");
//        Object consume = startRestartGroup.consume(CompositionLocalsKt.getLocalDensity());
//        ComposerKt.sourceInformationMarkerEnd(startRestartGroup);
//        Density density = (Density) consume;
//        ComposerKt.sourceInformationMarkerStart(startRestartGroup, 103361330, "C:CompositionLocal.kt#9igjgp");
//        Object consume2 = startRestartGroup.consume(CompositionLocalsKt.getLocalLayoutDirection());
//        ComposerKt.sourceInformationMarkerEnd(startRestartGroup);
//        LayoutDirection layoutDirection = (LayoutDirection) consume2;
//        Function0 constructor = ComposeUiNode.Companion.getConstructor();
//        Function3 materializerOf = LayoutKt.materializerOf(verticalScroll$default);
//        if (!(startRestartGroup.getApplier() instanceof Applier)) {
//            ComposablesKt.invalidApplier();
//        }
//        startRestartGroup.startReusableNode();
//        if (startRestartGroup.getInserting()) {
//            startRestartGroup.createNode(constructor);
//        } else {
//            startRestartGroup.useNode();
//        }
//        startRestartGroup.disableReusing();
//        Composer composer2 = Updater.constructor-impl(startRestartGroup);
//        Updater.set-impl(composer2, columnMeasurePolicy, ComposeUiNode.Companion.getSetMeasurePolicy());
//        Updater.set-impl(composer2, density, ComposeUiNode.Companion.getSetDensity());
//        Updater.set-impl(composer2, layoutDirection, ComposeUiNode.Companion.getSetLayoutDirection());
//        startRestartGroup.enableReusing();
//        materializerOf.invoke(SkippableUpdater.box-impl(SkippableUpdater.constructor-impl(startRestartGroup)), startRestartGroup, 0);
//        startRestartGroup.startReplaceableGroup(2058660585);
//        startRestartGroup.startReplaceableGroup(276693241);
//        ComposerKt.sourceInformation(startRestartGroup, "C73@3564L9:Column.kt#2w3rfo");
//        ColumnScope columnScope = ColumnScopeInstance.INSTANCE;
//        float f = 60;
//        SpacerKt.Spacer(SizeKt.height(Modifier.Companion, Dp, startRestartGroup, 6);
//        m256BuzzBellIconFNF3uiM(SizeKt.size(Modifier.Companion, Dp(75)), PainterResources_androidKt.painterResource((int) R.drawable.icon_location, startRestartGroup, 0), ColorResources_androidKt.colorResource((int) R.color.white, startRestartGroup, 0), startRestartGroup, 4166, 0);
//        SpacerKt.Spacer(SizeKt.height(Modifier.Companion, Dp.constructor-impl(50)), startRestartGroup, 6);
//        m255BoldText1DmQkMM(null, StringResources_androidKt.stringResource((int) R.string.use_your_location, startRestartGroup, 0), 0, ColorResources_androidKt.colorResource((int) R.color.white, startRestartGroup, 0), TextUnitKt.getSp(24), new TextStyle(0L, TextUnitKt.getSp(18), FontWeight.Companion.getBold(), (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (TextAlign) null, (TextDirection) null, 0L, (TextIndent) null, 262137, (DefaultConstructorMarker) null), 0, startRestartGroup, 16802304, 69);
//        SpacerKt.Spacer(SizeKt.height(Modifier.Companion, Dp.constructor-impl(20)), startRestartGroup, 6);
//        m257SemiBoldText1DmQkMM(null, StringResources_androidKt.stringResource((int) R.string.if_you_grant_intelligent_access_to_your_location_data_we_will_only_share, startRestartGroup, 0), 0, ColorResources_androidKt.colorResource((int) R.color.white, startRestartGroup, 0), TextUnitKt.getSp(14), new TextStyle(0L, TextUnitKt.getSp(16.5d), FontWeight.Companion.getSemiBold(), (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (TextAlign) null, (TextDirection) null, 0L, (TextIndent) null, 262137, (DefaultConstructorMarker) null), 0, startRestartGroup, 16802304, 69);
//        SpacerKt.Spacer(SizeKt.height(Modifier.Companion, Dp.constructor-impl(40)), startRestartGroup, 6);
//        m257SemiBoldText1DmQkMM(null, StringResources_androidKt.stringResource((int) R.string.if_you_grant_intelligent_access_to_your_location_data_always, startRestartGroup, 0), 0, ColorResources_androidKt.colorResource((int) R.color.white, startRestartGroup, 0), TextUnitKt.getSp(14), new TextStyle(0L, TextUnitKt.getSp(16.5d), FontWeight.Companion.getSemiBold(), (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (TextAlign) null, (TextDirection) null, 0L, (TextIndent) null, 262137, (DefaultConstructorMarker) null), 0, startRestartGroup, 16802304, 69);
//        SpacerKt.Spacer(SizeKt.height(Modifier.Companion, Dp.constructor-impl(f)), startRestartGroup, 6);
//        ButtonKt.TextButton(new Function0<Unit>() { // from class: com.sca.in_telligent.ui.locationprompt.LocationPromptActivity$LocationPromptComposable$1$1
//            /* JADX INFO: Access modifiers changed from: package-private */
//            {
//                super(0);
//            }
//
//            @Override // kotlin.jvm.functions.Function0
//            public /* bridge */ /* synthetic */ Unit invoke() {
//                invoke2();
//                return Unit.INSTANCE;
//            }
//
//            /* renamed from: invoke  reason: avoid collision after fix types in other method */
//            public final void invoke2() {
//                LocationPromptActivity.this.nextBtnTapped();
//            }
//        }, (Modifier) null, false, (MutableInteractionSource) null, (ButtonElevation) null, (Shape) null, (BorderStroke) null, (ButtonColors) null, (PaddingValues) null, ComposableLambdaKt.composableLambda(startRestartGroup, -819891122, true, new Function3<RowScope, Composer, Integer, Unit>() { // from class: com.sca.in_telligent.ui.locationprompt.LocationPromptActivity$LocationPromptComposable$1$2
//            /* JADX INFO: Access modifiers changed from: package-private */
//            {
//                super(3);
//            }
//
//            @Override // kotlin.jvm.functions.Function3
//            public /* bridge */ /* synthetic */ Unit invoke(RowScope rowScope, Composer composer3, Integer num) {
//                invoke(rowScope, composer3, num.intValue());
//                return Unit.INSTANCE;
//            }
//
//            public final void invoke(RowScope TextButton, Composer composer3, int i2) {
//                Intrinsics.checkNotNullParameter(TextButton, "$this$TextButton");
//                if (((i2 & 81) ^ 16) != 0 || !composer3.getSkipping()) {
//                    LocationPromptActivity.this.m257SemiBoldText1DmQkMM(null, StringResources_androidKt.stringResource((int) R.string.next, composer3, 0), 0, ColorResources_androidKt.colorResource((int) R.color.white, composer3, 0), 0L, new TextStyle(0L, TextUnitKt.getSp(16.5d), FontWeight.Companion.getSemiBold(), (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (TextAlign) null, (TextDirection) null, 0L, (TextIndent) null, 262137, (DefaultConstructorMarker) null), 0, composer3, 16777728, 85);
//                    return;
//                }
//                composer3.skipToGroupEnd();
//            }
//        }), startRestartGroup, 805306368, 510);
//        SpacerKt.Spacer(SizeKt.height-3ABfNKs(Modifier.Companion, Dp.constructor-impl(f)), startRestartGroup, 6);
//        startRestartGroup.endReplaceableGroup();
//        startRestartGroup.endReplaceableGroup();
//        startRestartGroup.endNode();
//        startRestartGroup.endReplaceableGroup();
//        startRestartGroup.endReplaceableGroup();
//        ScopeUpdateScope endRestartGroup = startRestartGroup.endRestartGroup();
//        if (endRestartGroup == null) {
//            return;
//        }
//        endRestartGroup.updateScope(new Function2<Composer, Integer, Unit>() {
//
//            @Override
//            public Unit invoke(Composer composer3, Integer num) {
//                invoke(composer3, num.intValue());
//                return Unit.INSTANCE;
//            }
//
//            public final void invoke(Composer composer3, int i2) {
//                LocationPromptActivity.this.LocationPromptComposable(composer3, i | 1);
//            }
//        });
//    }
//
//    public  void m257SemiBoldText1DmQkMM(androidx.compose.ui.Modifier r33, final String r34, int r35, long r36, long r38, final androidx.compose.ui.text.TextStyle r40, int r41, androidx.compose.runtime.Composer r42, final int r43, final int r44) {
//
//        throw new UnsupportedOperationException("Method not decompiled: com.sca.in_telligent.ui.locationprompt.LocationPromptActivity.m257SemiBoldText1DmQkMM(androidx.compose.ui.Modifier, java.lang.String, int, long, long, androidx.compose.ui.text.TextStyle, int, androidx.compose.runtime.Composer, int, int):void");
//    }
//
//
//    public  void m255BoldText1DmQkMM(androidx.compose.ui.Modifier r35, final String r36, int r37, long r38, long r40, final androidx.compose.ui.text.TextStyle r42, int r43, androidx.compose.runtime.Composer r44, final int r45, final int r46) {
//
//        throw new UnsupportedOperationException("Method not decompiled: com.sca.in_telligent.ui.locationprompt.LocationPromptActivity.m255BoldText1DmQkMM(androidx.compose.ui.Modifier, java.lang.String, int, long, long, androidx.compose.ui.text.TextStyle, int, androidx.compose.runtime.Composer, int, int):void");
//    }
//
//    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, d2 = {"Lcom/sca/in_telligent/ui/locationprompt/LocationPromptActivity$Companion;", "", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "getStartIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "app_productionRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
//    /* loaded from: C:\Users\BairesDev\Downloads\base-master_decoded_by_apktool\classes3.dex */
//    public static final class Companion {
//        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
//            this();
//        }
//
//        private Companion() {
//        }
//
//        public final String getTAG() {
//            return LocationPromptActivity.TAG;
//        }
//
//        public final Intent getStartIntent(Context context) {
//            return new Intent(context, LocationPromptActivity.class);
//        }
//    }
//
//    static {
//        Intrinsics.checkNotNullExpressionValue("LocationPromptActivity", "LocationPromptActivity::class.java.simpleName");
//        TAG = "LocationPromptActivity";
//    }
//
//    public void onRequestPermissionsResult(int i, String[] permissions, int[] grantResults) {
//        Intrinsics.checkNotNullParameter(permissions, "permissions");
//        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
//        super.onRequestPermissionsResult(i, permissions, grantResults);
//        if (i == 14) {
//            if (grantResults.length == 0) {
//                Log.i(TAG, "No permissions given");
//            } else if (grantResults.length > 0 && grantResults[0] == 0) {
//                if (Build.VERSION.SDK_INT >= 30) {
//                    if (getPackageManager().isAutoRevokeWhitelisted()) {
//                        return;
//                    }
//                    AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
//                    builder.setTitle(getString(R.string.would_you_like_to_enable_your_location_all_the_time)).setCancelable(false).setPositiveButton((int) R.string.yes, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.locationprompt.LocationPromptActivity$$ExternalSyntheticLambda0
//                        @Override // android.content.DialogInterface.OnClickListener
//                        public final void onClick(DialogInterface dialogInterface, int i2) {
//                            LocationPromptActivity.m253onRequestPermissionsResult$lambda1(LocationPromptActivity.this, dialogInterface, i2);
//                        }
//                    }).setNegativeButton((int) R.string.cancel, new DialogInterface.OnClickListener() { // from class: com.sca.in_telligent.ui.locationprompt.LocationPromptActivity$$ExternalSyntheticLambda1
//                        @Override // android.content.DialogInterface.OnClickListener
//                        public final void onClick(DialogInterface dialogInterface, int i2) {
//                            LocationPromptActivity.m254onRequestPermissionsResult$lambda2(LocationPromptActivity.this, dialogInterface, i2);
//                        }
//                    });
//                    AlertDialog create = builder.create();
//                    Intrinsics.checkNotNullExpressionValue(create, "builder.create()");
//                    create.show();
//                    return;
//                }
//                finish();
//            } else {
//                LocationUtil.setShouldShowStatus((Context) this);
//                finish();
//            }
//        }
//    }
//
//    public static final void m253onRequestPermissionsResult$lambda1(LocationPromptActivity this$0, DialogInterface dialog, int i) {
//        Intrinsics.checkNotNullParameter(this$0, "this$0");
//        Intrinsics.checkNotNullParameter(dialog, "dialog");
//        dialog.dismiss();
//        this$0.startActivity(new Intent("android.intent.action.AUTO_REVOKE_PERMISSIONS", Uri.parse(Intrinsics.stringPlus("package:", this$0.getPackageName()))));
//        this$0.finish();
//    }
//
//    public static final void m254onRequestPermissionsResult$lambda2(LocationPromptActivity this$0, DialogInterface dialog, int i) {
//        Intrinsics.checkNotNullParameter(this$0, "this$0");
//        Intrinsics.checkNotNullParameter(dialog, "dialog");
//        dialog.dismiss();
//        this$0.finish();
//    }
//}
