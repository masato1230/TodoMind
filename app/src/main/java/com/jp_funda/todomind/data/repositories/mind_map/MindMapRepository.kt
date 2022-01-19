package com.jp_funda.todomind.data.repositories.mind_map

import com.jp_funda.todomind.data.repositories.mind_map.entity.MindMap
import io.reactivex.rxjava3.core.Single
import io.realm.Realm

class MindMapRepository {

    fun createMindMap(mindMap: MindMap): Single<MindMap> {
        return Single.create { emitter ->
            Realm.getDefaultInstance().executeTransactionAsync({ realm ->
                realm.insert(mindMap)
            }, {
                emitter.onSuccess(mindMap)
            }, {
                emitter.onError(it)
            })
        }
    }

    

    // TODO getAllMindMaps
    // TODO getMindMap
    // TODO update MindMap
    // TODO delete MindMap
}