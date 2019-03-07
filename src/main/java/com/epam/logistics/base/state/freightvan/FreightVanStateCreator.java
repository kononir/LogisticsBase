package com.epam.logistics.base.state.freightvan;

import com.epam.logistics.base.entitie.FreightVan;

public class FreightVanStateCreator {
    private FreightVan freightVan;

    public FreightVanStateCreator(FreightVan freightVan) {
        this.freightVan = freightVan;
    }

    public FreightVanState create(String stateName) {
        FreightVanState result;

        if (stateName == null) {
            throw new EnumConstantNotPresentException(FreightVanStateName.class, null);
        }

        switch (FreightVanStateName.valueOf(stateName.toUpperCase())) {
            case LOADED:
                result = new LoadedState(freightVan);

                break;
            case UNLOADED:
                result = new UnloadedState(freightVan);

                break;
            case LOADED_WITH_PERISHABLE_GOODS:
                result = new LoadedWithPerishableGoodsState(freightVan);

                break;
            default:
                throw new EnumConstantNotPresentException(FreightVanStateName.class, stateName);
        }

        return result;
    }
}
