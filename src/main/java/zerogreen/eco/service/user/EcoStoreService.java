package zerogreen.eco.service.user;

import zerogreen.eco.entity.userentity.EcoStoreUser;

public interface EcoStoreService {

    public Long save(EcoStoreUser ecoStore);

    public void storeInfoSave(EcoStoreUser ecoStore);

}
