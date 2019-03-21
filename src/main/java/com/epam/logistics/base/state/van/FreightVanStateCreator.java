package com.epam.logistics.base.state.van;

public class FreightVanStateCreator {

    public FreightVanState create(String stateName) {
        FreightVanState result;

        if (stateName == null) {
            throw new EnumConstantNotPresentException(FreightVanStateName.class, null);
        }

        switch (FreightVanStateName.valueOf(stateName.toUpperCase())) {
            case LOADED:
                result = new LoadedState();

                break;
            case UNLOADED:
                result = new UnloadedState();

                break;
            case LOADED_WITH_PERISHABLE_GOODS:
                result = new LoadedWithPerishableGoodsState();

                break;
            default:
                throw new EnumConstantNotPresentException(FreightVanStateName.class, stateName);
        }

        return result;
    }
}
