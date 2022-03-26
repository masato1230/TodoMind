### Data Entities Proguard
-keep class * extends io.realm.RealmObject
-keep class * extends Parcelable

### Libraries Proguard
# Hilt
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel
