package ie.setu.swimspot.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.setu.swimspot.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "swimspots.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<SwimspotModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class SwimspotJSONStore(private val context: Context) : SwimspotStore {

    var swimspots = mutableListOf<SwimspotModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<SwimspotModel> {
        logAll()
        return swimspots
    }

    override fun create(swimspot: SwimspotModel) {
        swimspot.id = generateRandomId()
        swimspots.add(swimspot)
        serialize()
    }


    override fun update(swimspot: SwimspotModel) {
        val swimspotsList = findAll() as ArrayList<SwimspotModel>
        var foundSwimspot: SwimspotModel? = swimspotsList.find { p -> p.id == swimspot.id }
        if (foundSwimspot != null) {
            foundSwimspot.name = swimspot.name
            foundSwimspot.county = swimspot.county
            foundSwimspot.categorey = swimspot.categorey
            foundSwimspot.photo = swimspot.photo
            foundSwimspot.lat = swimspot.lat
            foundSwimspot.lng = swimspot.lng
            foundSwimspot.zoom = swimspot.zoom
        }
        serialize()
    }

    override fun delete(swimspot: SwimspotModel) {
        swimspots.remove(swimspot)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(swimspots, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        swimspots = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        swimspots.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}