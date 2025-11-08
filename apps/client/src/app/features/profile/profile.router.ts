import { Routes } from '@angular/router';

export const PROFILE_ROUTER: Routes = [
  {
    path: '',
    pathMatch:'prefix',
    loadComponent: () =>
      import('./components/images/images.component').then((c) => c.ImagesProfileComponent),
  },
  {
    path: 'collections',

    loadComponent: () =>
      import('./components/collections/collections.component').then(
        (c) => c.CollectionsProfileComponent
      ),
  },
  // {
  //   path: 'followings',
  //   loadComponent: () =>
  //     import('./components/following/following.component').then((c) => c.FollowingProfileComponent),
  // }
];

