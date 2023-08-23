package com.kmutswairo.dogbreedsapp.feature.dogbreeds.presentation.viewbreedimages;

import com.kmutswairo.dogbreedsapp.feature.dogbreeds.domain.usecase.GetBreedImagesUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class ViewDogBreedViewModel_Factory implements Factory<ViewDogBreedViewModel> {
  private final Provider<GetBreedImagesUseCase> getBreedImagesUseCaseProvider;

  public ViewDogBreedViewModel_Factory(
      Provider<GetBreedImagesUseCase> getBreedImagesUseCaseProvider) {
    this.getBreedImagesUseCaseProvider = getBreedImagesUseCaseProvider;
  }

  @Override
  public ViewDogBreedViewModel get() {
    return newInstance(getBreedImagesUseCaseProvider.get());
  }

  public static ViewDogBreedViewModel_Factory create(
      Provider<GetBreedImagesUseCase> getBreedImagesUseCaseProvider) {
    return new ViewDogBreedViewModel_Factory(getBreedImagesUseCaseProvider);
  }

  public static ViewDogBreedViewModel newInstance(GetBreedImagesUseCase getBreedImagesUseCase) {
    return new ViewDogBreedViewModel(getBreedImagesUseCase);
  }
}
