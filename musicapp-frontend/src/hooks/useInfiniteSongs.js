import { useInfiniteQuery } from 'react-query';
import { getSongs } from 'Services/SongService';

const DEFAULT_PAGE_SIZE = 10;

export const useInfiniteSongs = () => {
  return useInfiniteQuery(
    'infinite-songs',
    ({ pageParam = 0 }) => getSongs({ page: pageParam, size: DEFAULT_PAGE_SIZE }), // default page size 10
    {
      getNextPageParam: (lastPage, pages) => {
        if (lastPage.length < DEFAULT_PAGE_SIZE) return undefined; // Indicates there are no more pages as less than 10 items.
        return pages.length;
      },
    }
  );
};
