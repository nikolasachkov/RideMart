package com.ridemart.enums;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Make {
    BETA(List.of(Model.RR_300, Model.RR_250, Model.RX_300, Model.EVO_300, Model.EVO_250)),
    GASGAS(List.of(Model.EC_300, Model.EC_250, Model.MC_125, Model.MC_250, Model.MC_450, Model.TXT_300, Model.TXT_250)),
    HONDA(List.of(Model.CRF_250RX, Model.CRF_450RX, Model.CRF_250R, Model.CRF_450R, Model.AFRICA_TWIN, Model.FMX_650)),
    HUSQVARNA(List.of(Model.TE_300, Model.FE_350, Model.FE_501, Model.TC_125, Model.TC_250, Model.FC_450, Model.NORDEN_901, Model.HUSQVARNA_701_SUPERMOTO)),
    KAWASAKI(List.of(Model.KLX_300, Model.KX_250, Model.KX_450, Model.VERSYS_1000)),
    KTM(List.of(Model.EXC_300, Model.EXC_250, Model.EXC_500, Model.SX_125, Model.SX_250, Model.SX_450F, Model.SMC_690, Model.RC_390, Model._1290_SUPER_ADVENTURE, Model._890_ADVENTURE)),
    RIEJU(List.of(Model.MR_300, Model.MR_250)),
    SHERCO(List.of(Model.SE_300, Model.SE_250, Model.ST_300, Model.ST_250)),
    SUZUKI(List.of(Model.RMX_450Z, Model.RMZ_250, Model.RMZ_450, Model.VSTROM_1050, Model.DRZ_400SM)),
    TRIUMPH(List.of(Model.TF_250X, Model.TR_250X, Model.TIGER_900)),
    YAMAHA(List.of(Model.YZ_125, Model.YZ_250, Model.YZ_450F, Model.WR450F, Model.WR250F, Model.WR250X, Model.TENERE_700));

    private final List<Model> models;

    Make(List<Model> models) {
        this.models = models;
    }

    public List<String> getModels() {
        return models.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static Map<String, List<String>> getAllMakesAndModels() {
        return Map.ofEntries(
                Map.entry("BETA", BETA.getModels()),
                Map.entry("GASGAS", GASGAS.getModels()),
                Map.entry("HONDA", HONDA.getModels()),
                Map.entry("HUSQVARNA", HUSQVARNA.getModels()),
                Map.entry("KAWASAKI", KAWASAKI.getModels()),
                Map.entry("KTM", KTM.getModels()),
                Map.entry("RIEJU", RIEJU.getModels()),
                Map.entry("SHERCO", SHERCO.getModels()),
                Map.entry("SUZUKI", SUZUKI.getModels()),
                Map.entry("TRIUMPH", TRIUMPH.getModels()),
                Map.entry("YAMAHA", YAMAHA.getModels())
        );
    }
}
