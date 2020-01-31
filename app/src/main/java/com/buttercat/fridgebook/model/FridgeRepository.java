package com.buttercat.fridgebook.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.buttercat.fridgebook.model.apisource.SpoontacularApi;
import com.buttercat.fridgebook.model.apisource.model.Ingredient;
import com.buttercat.fridgebook.model.database.FridgeContentsDatabase;
import com.buttercat.fridgebook.model.database.FridgeItemDao;
import com.buttercat.fridgebook.model.database.FridgeListItem;

import java.util.List;

import retrofit2.Callback;

/**
 * A repository which provides access to information from the database
 */
public class FridgeRepository {

    /**
     * Singleton instance of this class
     */
    private static FridgeRepository sInstance;
    /**
     * Spoontacular API data source
     */
    private final SpoontacularApi mSpoontacularApi;
    /**
     * Singleton instance of {@link FridgeContentsDatabase}
     */
    private FridgeContentsDatabase mDatabase;
    /**
     * The {@link androidx.room.Room} DAO used to perform database operations
     */
    private FridgeItemDao fridgeItemDao;
    /**
     * A {@link MediatorLiveData<List<FridgeListItem>>} linked with the database
     */
    private MediatorLiveData<List<FridgeListItem>> liveFridgeList;

    /**
     * Default constructor which links the database livedata and provides access to the DAO
     *
     * @param fridgeContentsDatabase a static reference of {@link FridgeContentsDatabase}
     * @param spoontacularApi        the Spoontacular API data source
     */
    private FridgeRepository(final FridgeContentsDatabase fridgeContentsDatabase,
                             SpoontacularApi spoontacularApi) {
        mSpoontacularApi = spoontacularApi;
        mDatabase = fridgeContentsDatabase;
        fridgeItemDao = mDatabase.fridgeItemDao();
        liveFridgeList = new MediatorLiveData<>();
        liveFridgeList.addSource(fridgeItemDao.getFridgeContentsLiveData(), productEntities -> {
            if (mDatabase.getDatabaseCreated().getValue() != null) {
                liveFridgeList.postValue(productEntities);
            }
        });

    }

    /**
     * @param database        a singleton {@link FridgeContentsDatabase} in case the instance must
     *                        be created
     * @param spoontacularApi the Spoontacular API data source
     * @return a static instance of this class, {@link FridgeRepository}
     */
    public static FridgeRepository getInstance(final FridgeContentsDatabase database,
                                               SpoontacularApi spoontacularApi) {
        if (sInstance == null) {
            synchronized (FridgeRepository.class) {
                if (sInstance == null) {
                    sInstance = new FridgeRepository(database, spoontacularApi);
                }
            }
        }
        return sInstance;
    }

    /**
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     *
     * @return a {@link LiveData<List<FridgeListItem>>} with the latest contents from the database
     */
    public LiveData<List<FridgeListItem>> getFridgeContentsLiveData() {
        return liveFridgeList;
    }

    /**
     * You must call this on a non-UI thread or your app will throw an exception. Room ensures
     * that you're not doing any long running operations on the main thread, blocking the UI.
     *
     * @param fridgeItem the {@link FridgeListItem} to be inserted into the database
     */
    public void insert(FridgeListItem fridgeItem) {
        fridgeItemDao.insert(fridgeItem);
    }

    /**
     * Obtain a {@link List<Ingredient>} asynchronously, with a query and set a {@link Callback}
     * that will be called when the list is obtained by {@link retrofit2.Retrofit}
     *
     * @param query        the {@link String} used to search ingredients
     * @param limit        the maximum size of the {@link List}
     * @param respCallback a {@link Callback} which is called when the query has completed
     */
    public void fetchIngredientsWithQuery(String query, int limit, Callback<List<Ingredient>> respCallback) {
        mSpoontacularApi.fetchIngredientsList(query, limit, respCallback);
    }
}
