package ie.setu.swimspot.models

interface SwimspotStore {
    fun findAll(): List<SwimspotModel>
    fun create(swimspot: SwimspotModel)
    fun update(swimspot: SwimspotModel)
    fun delete(swimspot: SwimspotModel)
    fun findById(id:Long) : SwimspotModel?
}