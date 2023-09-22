import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pemesanan_hotel.Model.GetHotel
import com.example.pemesanan_hotel.Model.HotelModel
import com.example.pemesanan_hotel.Repository.HotelRepository

class HotelViewModel : AndroidViewModel {

    private val hotelRepository: HotelRepository

    constructor(application: Application) : super(application) {
        hotelRepository = HotelRepository(application)
    }

    fun loadData(nama: String): LiveData<List<HotelModel>> {
        return hotelRepository.loadData(nama)
    }

    fun delete(id: String): LiveData<Boolean> {
        return hotelRepository.delete (id)
    }

    fun getDetail(id: String): LiveData<HotelModel> {
        return hotelRepository.detail(id)
    }

    fun editHotel(hotel: HotelModel): LiveData<Boolean> {
        return hotelRepository.edit(hotel)
    }

    fun insertHotel(hotel: HotelModel): LiveData<Boolean> {
        return hotelRepository.insertHotel(hotel)
    }

    fun getHotelList(): MutableLiveData<List<GetHotel>> {
        return hotelRepository.getHotelList()
    }
}