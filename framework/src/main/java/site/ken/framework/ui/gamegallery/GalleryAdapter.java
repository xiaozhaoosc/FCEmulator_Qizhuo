package site.ken.framework.ui.gamegallery;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

import site.ken.framework.R;
import site.ken.framework.gamedata.dao.entity.GameEntity;
//
public class GalleryAdapter extends BaseAdapter implements SectionIndexer {

    public static final int SORT_BY_NAME_ALPHA = 0;
    public static final int SORT_BY_INSERT_DATE = 1;
    public static final int SORT_BY_MOST_PLAYED = 2;
    public static final int SORT_BY_LAST_PLAYED = 3;

    private HashMap<Character, Integer> alphaIndexer = new HashMap<>();
    private String filter = "";
    private Character[] sections;
    private LayoutInflater inflater;
    private Context context;
    private int mainColor;
    private ArrayList<GameEntity> games = new ArrayList<>();
    private ArrayList<RowItem> filterGames = new ArrayList<>();
    private int sumRuns = 0;
    private int sortType = SORT_BY_NAME_ALPHA;

    private Comparator<GameEntity> nameComparator = (lhs, rhs) ->
            lhs.getSortName().compareTo(rhs.getSortName());

    private Comparator<GameEntity> insertDateComparator = (lhs, rhs) ->
            (int) (-lhs.inserTime + rhs.inserTime);

    private Comparator<GameEntity> lastPlayedDateComparator = (lhs, rhs) -> {
        long dif = lhs.lastGameTime - rhs.lastGameTime;
        if (dif == 0) {
            return 0;
        } else if (dif < 0) {
            return 1;
        } else {
            return -1;
        }
    };
    private Comparator<GameEntity> playedCountComparator = (lhs, rhs) ->
            -lhs.runCount + rhs.runCount;

    public GalleryAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mainColor = context.getResources().getColor(R.color.main_color);
    }

    @Override
    public int getCount() {
        return filterGames.size();
    }

    @Override
    public Object getItem(int position) {
        return filterGames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RowItem item = filterGames.get(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_game_list, null);//游戲列表
        }
        GameEntity game = item.game;
        TextView name = convertView.findViewById(R.id.row_game_item_name);
        ImageView arrowIcon = convertView.findViewById(R.id.game_item_arrow);
        ImageView bck = convertView.findViewById(R.id.game_item_bck);
        ProgressBar runIndicator = convertView.findViewById(R.id.row_game_item_progressBar);
        runIndicator.setMax(sumRuns);
        name.setText("【"+position+"】"+game.getCleanName());
        arrowIcon.setImageResource(R.drawable.ic_next_arrow);
        arrowIcon.clearAnimation();
        name.setTextColor(mainColor);
        name.setGravity(Gravity.CENTER_VERTICAL);
        bck.setImageResource(R.drawable.game_item_small_bck);
        return convertView;
    }

    public void setFilter(String filter) {
        this.filter = filter.toLowerCase();
        filterGames();
    }

    public void setGames(ArrayList<GameEntity> games) {
        this.games = new ArrayList<>(games);
        filterGames();
    }

    public int addGames(ArrayList<GameEntity> newGames) {
        for (GameEntity game : newGames) {
            if (!games.contains(game)) {
                games.add(game);
            }
        }
        filterGames();
        return games.size();
    }

    private void filterGames() {
        filterGames.clear();
        switch (sortType) {
            case SORT_BY_NAME_ALPHA:
                Collections.sort(games, nameComparator);
                break;
            case SORT_BY_INSERT_DATE:
                Collections.sort(games, insertDateComparator);
                break;
            case SORT_BY_MOST_PLAYED:
                Collections.sort(games, playedCountComparator);
                break;
            case SORT_BY_LAST_PLAYED:
                Collections.sort(games, lastPlayedDateComparator);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sortType);
        }
        String containsFilter = " " + filter;
        sumRuns = 0;
        for (GameEntity game : games) {
            sumRuns = game.runCount > sumRuns ? game.runCount : sumRuns;
            String name = game.getCleanName().toLowerCase();
            boolean secondCondition = true;
            if (sortType == SORT_BY_LAST_PLAYED || sortType == SORT_BY_MOST_PLAYED) {
                secondCondition = game.lastGameTime != 0;
            }
            if ((name.startsWith(filter) || name.contains(containsFilter)) & secondCondition) {
                RowItem item = new RowItem();
                item.game = game;
                item.firstLetter = name.charAt(0);
                filterGames.add(item);
            }
        }

        alphaIndexer.clear();
        if (sortType == SORT_BY_NAME_ALPHA) {
            for (int i = 0; i < filterGames.size(); i++) {
                RowItem item = filterGames.get(i);
                char ch = item.firstLetter;
                if (!alphaIndexer.containsKey(ch)) {
                    alphaIndexer.put(ch, i);
                }
            }
        }
        super.notifyDataSetChanged();
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
        filterGames();
    }

    @Override
    public int getPositionForSection(int section) {
        try {
            Character ch = Character.toLowerCase(sections[section]);
            Integer pos = alphaIndexer.get(ch);
            if (pos == null) {
                return 0;
            } else {
                return pos;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    @Override
    public int getSectionForPosition(int position) {
        RowItem item = (RowItem) getItem(position);
        char ch = Character.toUpperCase(item.firstLetter);
        for (int i = 0; i < sections.length; i++) {
            Character ch1 = sections[i];
            if (ch1.equals(ch)) {
                return i;
            }
        }
        return 1;
    }

    @Override
    public Object[] getSections() {
        Set<Character> keyset = alphaIndexer.keySet();
        sections = new Character[keyset.size()];
        keyset.toArray(sections);
        Arrays.sort(sections, Character::compareTo);
        for (int i = 0; i < sections.length; i++) {
            sections[i] = Character.toUpperCase(sections[i]);
        }
        return sections;
    }

    @Override
    public void notifyDataSetChanged() {
        filterGames();
    }

    public class RowItem {
        GameEntity game;
        char firstLetter;
    }

}