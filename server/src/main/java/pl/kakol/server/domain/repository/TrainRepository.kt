package pl.kakol.server.domain.repository

import pl.kakol.server.domain.model.Train

interface TrainRepository {
    fun getAll(): List<Train>
    fun getById(id: String): Train?
    fun add(train: Train)
    fun update(id: String, newName: String?, newInfoUrl: String?, newCompleted: Boolean?): Boolean
    fun delete(id: String): Boolean
}